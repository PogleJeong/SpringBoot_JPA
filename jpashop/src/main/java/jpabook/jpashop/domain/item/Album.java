package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A") // SINGLE TABLE 전략 사용시 객체를 구분할 VALUE 명
@Getter
@Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
