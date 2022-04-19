package com.campssg.service;

import com.campssg.DB.entity.ChecklistProduct;
import com.campssg.DB.repository.ChecklistProductRepository;
import com.campssg.dto.checklistProduct.ChecklistResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChecklistProductService {

    private final ChecklistProductRepository checklistProductRepository;

    // 카테고리별로 물품 리스트 반환
    public List<ChecklistResponseDto> getChecklistProduct(Long categoryId) {
        List<ChecklistProduct> checklistProducts = checklistProductRepository.findByCategory_categoryId(categoryId);
        return checklistProducts.stream().map(checklistProduct -> new ChecklistResponseDto(checklistProduct)).collect(Collectors.toList());
    }
}
