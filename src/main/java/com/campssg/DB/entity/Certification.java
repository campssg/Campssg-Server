package com.campssg.DB.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "certification")
public class Certification extends Auditor {
    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "certification_number")
    private String certificationNumber;
}
