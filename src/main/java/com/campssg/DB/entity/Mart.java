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
@Table(name = "mart")
public class Mart extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mart_id")
    private Long martId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "mart_name")
    private String martName;

    @Column(name = "mart_address")
    private String martAddress;

    @Column(name = "mart_number")
    private String martNumber;

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;

    @Column(name = "request_yn")
    private Long requestYn;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Builder
    public Mart(Long martId, User user, String martName,  String martNumber, String martAddress, Long requestYn, String openTime,
        String closeTime, Double latitude, Double longitude) {
        this.martId = martId;
        this.user = user;
        this.martName = martName;
        this.martAddress = martAddress;
        this.martNumber = martNumber;
        this.requestYn = requestYn;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Builder(builderClassName = "ByMartIdBuilder", builderMethodName = "ByMartIdBuilder")
    public Mart(Long martId) {
        this.martId = martId;
    }

    public void updateMartName(String martName) {this.martName = martName;}
    public void updateOpenTime(String openTime) {this.openTime = openTime;}
    public void updateCloseTime(String closeTime) {this.closeTime = closeTime;}
    public void updateRequestYn(Long requestYn) {this.requestYn = requestYn;}
}
