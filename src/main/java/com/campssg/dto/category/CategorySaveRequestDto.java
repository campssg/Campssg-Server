package com.campssg.dto.category;

import com.campssg.DB.entity.Category;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "카테고리 저장 요청")
public class CategorySaveRequestDto {

    private String categoryName;

    public Category toEntity(String categoryName) {
        return Category.builder()
            .categoryName(categoryName)
            .build();
    }
}
