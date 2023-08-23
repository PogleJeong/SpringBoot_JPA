package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;
    
    @Enumerated(EnumType.STRING) // enum 을 가지는 경우(무조건 EnumType.STRING 사용)
    private DeliveryStatus status; // READY or COMP
}
