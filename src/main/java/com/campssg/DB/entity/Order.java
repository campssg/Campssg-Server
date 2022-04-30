package com.campssg.DB.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order extends Auditor {
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "mart_id", referencedColumnName = "mart_id")
    private Mart mart;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "reserved_at")
    private LocalDateTime reservedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_state")
    private OrderState orderState;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "charge")
    private int charge;

    @Column(name = "qrcode_url")
    private String qrcodeUrl;

    public void updateStatus(OrderState status) {
        this.orderState = status;
    }

    public void updateQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }
}
