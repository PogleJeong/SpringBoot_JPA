package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // table 명 명시
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 (실무에선 반드시 LAZY 방식으로 - * to ONE 에 적용)
    @JoinColumn(name="member_id") // mapping 할 컬럼(FK)
    private Member member;
    // ORDERS TABLE 의 member 를 바꾸면 MEMBER TABLE 이 변경되게, 연관관계 주인을 설정(ORDER TABLE - member)

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간 hibernate 가 자동지원

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 (ORDER, CALCEL)

    //== 연관관계 (+편의)메서드 ==//
    // 양방향 연관관계가 있을 시 사용하면 편리함
    public void setMember(Member member) { // Order.member - Member.member_id
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) { // Order.orderItems - OrderItem.order
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드: 단순한 생성이 아닌 연관관계가 많이 얽혀있는 객체의 경우 생성 메서드사용 ==//
    public static Order createOrder(Member member,  Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    
    //== 비즈니스 로직==//
    /**
     * 주문취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // orderItem 에서도 취소필요
        }
    }

    //== 조회로직 ==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
        // return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

}
