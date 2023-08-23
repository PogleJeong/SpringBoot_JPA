package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M") // SINGLE TABLE 전략 사용시 객체를 구분할 VALUE 명
@Getter @Setter
public class Movie extends Item {

    private String director;
    private String actor;

}
