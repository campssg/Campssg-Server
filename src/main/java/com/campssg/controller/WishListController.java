package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.camping.CampWishRequestDto;
import com.campssg.dto.camping.CampWishResponseDto;
import com.campssg.dto.mart.MartListResponseDto;
import com.campssg.service.WishService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wish")
public class WishListController {

    private final WishService wishService;

    @ApiOperation(value = "캠핑장 즐겨찾기 추가")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "캠핑장 즐겨찾기 추가 완료")
    })
    @PostMapping("/add/camp")
    public ResponseEntity<ResponseMessage> addCampWish(@RequestBody @Valid CampWishRequestDto campWishRequestDto) {
        wishService.addCampWish(campWishRequestDto);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "캠핑장 즐겨찾기 추가 성공"));
    }

    @ApiOperation(value = "캠핑장 즐겨찾기 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "캠핑장 즐겨찾기 조회 완료")
    })
    @GetMapping("/get/camp")
    public ResponseEntity<List<CampWishResponseDto>> getCampWish() {
        return ResponseEntity.ok(wishService.getCampWish());
    }

    @ApiOperation(value = "마트 즐겨찾기 추가")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마트 즐겨찾기 추가 완료")
    })
    @PostMapping("/add/{martId}")
    public ResponseEntity<ResponseMessage> addMartWish(@PathVariable Long martId) {
        wishService.addMartWish(martId);
        return ResponseEntity.ok().body(ResponseMessage.res(HttpStatus.OK, "마트 즐겨찾기 추가 성공"));
    }

    @ApiOperation(value = "마트 즐겨찾가 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마트 즐겨찾기 조회 완료")
    })
    @GetMapping("/get/mart")
    public ResponseEntity<List<MartListResponseDto>> getMartWish() {
        return ResponseEntity.ok(wishService.getMartWish());
    }
}
