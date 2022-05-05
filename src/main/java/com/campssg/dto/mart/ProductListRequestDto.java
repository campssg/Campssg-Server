package com.campssg.dto.mart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductListRequestDto {
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "검색할 키워드")
    private String productName;
}
