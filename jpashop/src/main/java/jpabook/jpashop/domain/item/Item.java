package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // SINGLE TALBE 전략 사용 (Book, Album, Movie 가 모두 Item table 안에 존재)
@DiscriminatorColumn(name = "dtype") // SINGLE TABLE 전략시 적용할 객체를 구별할 컬럼명
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//

    /*
        도메인 주도 설계 -

        Entity 자체가 해결할 수 있는 기능 등은 Entity 안에 비즈니스 로직을 작성하는 것이 좋음(객체지향적임)
        stockQuantity 가 Item 이라는 Entity 안에 속해있음. 데이터를 가지고 있으므로 응집도가 있음.
    */

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock"); // 사용자설정 exception 만들 수 있음.
        }
        this.stockQuantity = restStock;
    }
}

