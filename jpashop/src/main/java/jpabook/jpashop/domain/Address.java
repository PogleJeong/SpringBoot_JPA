package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // jpa 의 내장타입 - 어딘가에 내장될 수 있음을 의미
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 엔티티나 임베디드타입은 기본생성자 필요(public or protected)
    protected Address() {

    }
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
