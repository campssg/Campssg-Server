package com.campssg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SmsCertificationRequestDto {
    private String phoneNumber;
    private String certificationNumber;
}
