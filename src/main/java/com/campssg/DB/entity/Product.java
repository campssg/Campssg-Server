package com.campssg.DB.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "mart_id", referencedColumnName = "mart_id")
    private Mart mart;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private String productPrice;

    @Column(name = "product_stock")
    private int productStock;

    @Column(name = "product_img_url")
    private String productImgUrl;

    @Builder
    public Product(Category category, Mart mart, String productName, String productPrice, int productStock,
        String productImgUrl) {
        this.category = category;
        this.mart = mart;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productImgUrl = productImgUrl;
    }
}
