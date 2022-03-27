package com.campssg.DB.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "cart")
public class Cart extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "total_count")
    private int totalCount;

    @Column(name = "total_price")
    private int totalPrice;

    public void addTotalCount(int count) {
        this.totalCount += count;
    }

    public void addTotalPrice(int count, int price) {
        int productPrice = price;
        this.totalPrice += count * productPrice;
    }
}
