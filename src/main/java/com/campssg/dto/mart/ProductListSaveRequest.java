package com.campssg.dto.mart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductListSaveRequest {
    private String productName;
    private Long categoryId;
    private int productPrice;
    private String productImgUrl;
    private int productStock;
}
