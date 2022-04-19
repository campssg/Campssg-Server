package com.campssg.controller;

import com.campssg.dto.checklistProduct.ChecklistResponseDto;
import com.campssg.service.ChecklistProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checklist")
public class ChecklistProductController {

    private final ChecklistProductService checklistProductService;

    @ApiOperation(value = "카테고리별 물품 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "카테고리별 물품 리스트 조회 성공")
    })
    @GetMapping("/{categoryId}")
    public List<ChecklistResponseDto> getChecklistProduct(@PathVariable Long categoryId) {
        return checklistProductService.getChecklistProduct(categoryId);
    }
}
