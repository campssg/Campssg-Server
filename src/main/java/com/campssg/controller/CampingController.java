package com.campssg.controller;

import com.campssg.dto.camping.CampingRequestDto;
import com.campssg.dto.camping.CampingResponseDto;
import com.campssg.service.CampingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camping")
public class CampingController {
    private final CampingService campingService;

    @ApiOperation(value = "키워드 기반 캠핑장 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "캠핑장 조회 성공")
    })
    @GetMapping("/{keyword}")
    public ResponseEntity<List<CampingResponseDto>> searchKeyword(@PathVariable String keyword) throws IOException, ParseException {
        return ResponseEntity.ok(campingService.searchKeyword(keyword));
    }

    @ApiOperation(value = "위치 기반 캠핑장 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "캠핑장 조회 성공")
    })
    @GetMapping("/place")
    public ResponseEntity<List<CampingResponseDto>> searchPlace(@RequestBody @Valid CampingRequestDto requestDto) throws IOException, ParseException {
        return ResponseEntity.ok(campingService.searchPlace(requestDto));
    }
}
