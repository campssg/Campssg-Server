package com.campssg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SendSmsRequestDto {
    private String phoneNumber;
    private String title;
    private String content;
}
