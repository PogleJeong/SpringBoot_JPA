package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 데이터를 읽기만 하는경우 transaction(readonly)를 적용해주면 성능 증가
@RequiredArgsConstructor // lombok - final 키워드를 가지고 있는 객체에 의존성주입(DI)
public class MemberService {

    private final MemberRepository memberRepository;

    /* 생성자 주입 사용 (@Autowired 생략가능)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */

    /**
     * 회원가입 (중복이름금지)
     */
    @Transactional // jpa 의 모든 데이터변경이나 모든 로직들은 가급적이면 transaction 안에서 실행되야함, 쓰기를 하므로 transaction(readonly=false 기본값) 사용
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복회원검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        // Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원전체 조회
     */
    @Transactional(readOnly = true) // 데이터를 읽기만 하는경우 transaction(readonly)를 적용해주면 성능 증가
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원단일 조회
     * */
    @Transactional(readOnly = true) // 데이터를 읽기만 하는경우 transaction(readonly)를 적용해주면 성능 증가
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
