package com.campssg.dto.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    @ApiModelProperty(value = "예약 날짜")
    private String reservedDate;

    @ApiModelProperty(value = "예약 시간")
    private String reservedTime;
}
