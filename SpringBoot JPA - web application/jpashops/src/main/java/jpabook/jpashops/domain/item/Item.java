package jpabook.jpashops.domain.item;

import jpabook.jpashops.domain.Category;
import jpabook.jpashops.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stackQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//

    /**
     * stock 증가
     */
    public void addStack(int quantity) {
        this.stackQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStack(int quantity) {
        int restStock = this.stackQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stackQuantity = restStock;
    }
}
