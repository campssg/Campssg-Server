package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.category.CategorySaveRequestDto;
import com.campssg.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "카테고리 등록")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "카테고리 등록 완료")
    })
    @PostMapping
    public ResponseEntity<ResponseMessage> saveMart(
        @RequestBody @Validated CategorySaveRequestDto requestDto) {
        categoryService.saveCategory(requestDto);
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "카테고리 등록 성공", null), HttpStatus.OK);
    }
}
