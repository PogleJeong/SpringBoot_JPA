package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest // junit5 에서는 @RunWith(SpringRunner.class) 가 포함되어있음
@Transactional // 데이터 변경 및 롤백
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member= new Member();
        member.setName("pogle");

        // when
        Long savedId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        Member member2 = new Member();

        member1.setName("pogle");
        member2.setName("pogle");

        // when
        memberService.join(member1);
        Assertions.assertThrows(IllegalStateException.class, ()-> {
            memberService.join(member2);
        });
        
        // then
        // Assertions.fail("예외가 발생해야 한다."); // 이쪽코드가 실행되면 예외발생

    }

}