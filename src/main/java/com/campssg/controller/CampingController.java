package com.campssg.controller;

import com.campssg.dto.camping.CampingResponseDto;
import com.campssg.service.CampingService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camping")
public class CampingController {
    private final CampingService campingService;

    @GetMapping("/{keyword}")
    public ResponseEntity<List<CampingResponseDto>> searchKeyword(@PathVariable String keyword) throws IOException, ParseException {
        return ResponseEntity.ok(campingService.searchKeyword(keyword));
    }
}
