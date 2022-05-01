package com.campssg.dto.checklistProduct;

import com.campssg.DB.entity.ChecklistProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChecklistResponseDto {
    private String categoryName;
    private Long categoryId;
    private String productName;
    private int productPrice;
    private String productImgUrl;

    public ChecklistResponseDto(ChecklistProduct checklistProduct) {
        this.categoryName = checklistProduct.getCategory().getCategoryName();
        this.categoryId = checklistProduct.getCategory().getCategoryId();
        this.productName = checklistProduct.getProductName();
        this.productPrice = checklistProduct.getProductPrice();
        this.productImgUrl = checklistProduct.getProductImgUrl();
    }
}
