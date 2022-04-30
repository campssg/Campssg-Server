package com.campssg.dto.mart;

import com.campssg.DB.entity.Product;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductListResponse {

    // 카테고리명
    private String categoryName;

    private List<ProductList> productList;

    public ProductListResponse(List<ProductList> productLists) {
        this.productList = productLists;
    }

    @Getter
    public class ProductList {
        private Long productId;

        private String productName;

        private int productPrice;

        private int productStock;

        private String productImgUrl;

        public ProductList(Product product) {
            this.productId = product.getProductId();
            this.productName = product.getProductName();
            this.productPrice = product.getProductPrice();
            this.productStock = product.getProductStock();
            this.productImgUrl = product.getProductImgUrl();
        }
    }
}
