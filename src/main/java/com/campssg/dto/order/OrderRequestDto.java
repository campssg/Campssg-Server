package com.campssg.dto.order;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    @NotNull
    @ApiModelProperty(value = "예약 날짜")
    private String reservedDate;

    @NotNull
    @ApiModelProperty(value = "예약 시간")
    private String reservedTime;
}
