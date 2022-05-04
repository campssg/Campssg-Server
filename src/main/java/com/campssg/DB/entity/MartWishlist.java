package com.campssg.DB.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "mart_wishlist")
public class MartWishlist extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mart_wishlist_id")
    private Long martWishlistId;

    @ManyToOne
    @JoinColumn(name = "mart_id", referencedColumnName = "mart_id")
    private Mart mart;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
