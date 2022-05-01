package com.campssg.DB.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "requested_product")
public class RequestedProduct extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requested_product_id")
    private Long requestedProductId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "mart_id", referencedColumnName = "mart_id")
    private Mart mart;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "requested_product_name")
    private String requestedProductName;

    @Column(name = "requested_product_price")
    private int requestedProductPrice;

    @Column(name = "requested_product_count")
    private int requestedProductCount;

    @Column(name = "requested_product_reference")
    private String requestedProductReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "requested_product_state")
    private RequestedProductState requestedProductState;
}
