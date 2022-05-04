package com.campssg.DB.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "camp_wishlist")
public class CampWishlist extends Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camp_wishlist_id")
    private Long campWishlistId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "camp_name")
    private String campName;

    @Column(name = "camp_address")
    private String campAddress;

    @Column(name = "camp_tel")
    private String campTel;

    @Column(name = "camp_latitude")
    private Double campLatitude;

    @Column(name = "camp_longitude")
    private Double campLongitude;
}
