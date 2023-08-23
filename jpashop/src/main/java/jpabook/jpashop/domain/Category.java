package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany// 다대다 관계 예제를 보여주기 위한 예시 (원래는 다대다를 일대다 2개로 나누어야함)
    @JoinTable(
            name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    ) // 중간테이블 생성(관계형 DB 에서는 다대다를 나타낼 수 없어서 중간테이블이 있어야함)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 (편의)메서드==//ㅑ
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

}
