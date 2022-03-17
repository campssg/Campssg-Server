package com.campssg.dto.mart;

import com.campssg.DB.entity.Category;
import com.campssg.DB.entity.Mart;
import com.campssg.DB.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "마트 상품 등록 요청")
public class ProductSaveRequest {

    @NotNull(message = "카테고리 식별번호는 필수입니다.")
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "카테고리 식별번호")
    private Long categoryId;

    @ApiModelProperty(position = 2, hidden = true, dataType = "String")
    private Long martId;

    @NotNull(message = "상품명은 필수입니다.")
    @ApiModelProperty(position = 3, required = true, dataType = "String", value = "상품명")
    private String productName;

    @NotNull(message = "상품가격은 필수입니다.")
    @ApiModelProperty(position = 4, required = true, dataType = "String", value = "상품가격")
    private String productPrice;

    @NotNull(message = "상품 재고는 필수입니다.")
    @ApiModelProperty(position = 5, required = true, dataType = "int", value = "상품 재고")
    private int productStock;

    @ApiModelProperty(position = 6, hidden = true, dataType = "String", value = "상품 이미지")
    private String productImgUrl;

    public Product toEntity() {
        return Product.builder()
            .category(new Category(categoryId))
            .mart(new Mart(martId))
            .productName(productName)
            .productPrice(productPrice)
            .productStock(productStock)
            .productImgUrl(productImgUrl)
            .build();
    }
}
