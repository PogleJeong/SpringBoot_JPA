package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    
    private String name;
    
    @Embedded // 내장타입을 포함한 객체임을 의미
    private Address address;
    
    @OneToMany(mappedBy = "member") // 일대다 관계임을 명시(+orders table 의 member 컬럼에 의해 매핑됨, orders.member 의 거울)
    private List<Order> orders = new ArrayList<>();
}
