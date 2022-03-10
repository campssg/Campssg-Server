package com.campssg.DB.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
import org.springframework.data.annotation.CreatedDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "mart")
public class Mart {

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

    @Column(name = "open_time")
    private String openTime;

    @Column(name = "close_time")
    private String closeTime;

    @Column(name = "request_yn")
    private Long requestYn;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Builder
    public Mart(Long martId, User user, String martName, String martAddress, Long requestYn, String openTime,
        String closeTime) {
        this.martId = martId;
        this.user = user;
        this.martName = martName;
        this.martAddress = martAddress;
        this.requestYn = requestYn;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    @Builder(builderClassName = "ByMartIdBuilder", builderMethodName = "ByMartIdBuilder")
    public Mart(Long martId) {
        this.martId = martId;
    }
}
