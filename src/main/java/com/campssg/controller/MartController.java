package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.mart.*;
import com.campssg.service.MartService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/mart")
@RequiredArgsConstructor
public class MartController {

    @Autowired
    private final MartService martService;

    @ApiOperation(value = "마트 등록")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "마트 등록 완료")
    })
    @PostMapping
    public ResponseEntity<ResponseMessage> saveMart(
        @RequestBody @Validated MartSaveRequestDto requestDto) {
        martService.saveMart(requestDto);
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "마트 등록 성공", null), HttpStatus.OK);
    }

    @ApiOperation(value = "마트 조회")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "마트 조회 완료")
    })
    @GetMapping("/info")
    public ResponseEntity<ResponseMessage<List<MartListResponseDto>>> martList() {
        List<MartListResponseDto> response = martService.findByUserId();
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "마트 조회 성공", response), HttpStatus.OK);
    }

    @ApiOperation(value = "해당 마트에 상품 등록")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "해당 마트에 상품 등록")
    })
    @PostMapping("/{martId}")
    public ResponseEntity<ResponseMessage> saveProductToMart(
        @PathVariable Long martId,
        @ModelAttribute @Valid ProductSaveRequest requestDto,
        @RequestPart(value = "img", required = false) MultipartFile file) throws IOException {
        requestDto.setMartId(martId);
        martService.saveProductToMart(requestDto, file);
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "해당 마트에 상품 등록", null), HttpStatus.OK);
    }

    @ApiOperation(value = "해당 마트에 상품 리스트 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "상품 리스트 등록 성공")
    })
    @PostMapping("/{martId}/list")
    public ResponseEntity<ResponseMessage> saveProductListToMart(@PathVariable Long martId, @Valid @RequestBody List<ProductListSaveRequest> checklistProducts) {
        martService.saveProductListToMart(checklistProducts, martId);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "물품 등록이 완료되었습니다"));
    }

    @ApiOperation(value = "마트 상품 조회 성공")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "마트 상품 조회 성공")
    })
    @GetMapping("/product/{martId}")
    public ResponseEntity<ResponseMessage<ProductListResponse>> findProductByMartId(
        @PathVariable Long martId) {
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "마트 상품 조회 성공", martService.findProductByMartId(martId)), HttpStatus.OK);
    }

    @ApiOperation(value = "마트 상품 재고 추가")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "마트 상품 재고 추가 성공")
    })
    @PostMapping("/{productId}/{count}")
    public ResponseEntity<ResponseMessage> addProductStock(@PathVariable Long productId, @PathVariable int count) {
        martService.addProductStock(productId, count);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "재고 추가가 완료되었습니다"));
    }

    @ApiOperation(value = "마트 상품 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "마트 상품 삭제 성공")
    })
    @PostMapping("/delete/{productId}")
    public ResponseEntity<ResponseMessage> addProductStock(@PathVariable Long productId) {
        martService.deleteProduct(productId);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "삭제가 완료되었습니다"));
    }
}
