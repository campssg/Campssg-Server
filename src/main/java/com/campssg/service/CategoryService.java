package com.campssg.service;

import com.campssg.DB.repository.CategoryRepository;
import com.campssg.dto.category.CategorySaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void saveCategory(CategorySaveRequestDto requestDto) {
        categoryRepository.save(requestDto.toEntity(requestDto.getCategoryName()));
    }
}
