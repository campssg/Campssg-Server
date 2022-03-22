package com.campssg.controller;

import com.campssg.dto.camping.CampingRequestDto;
import com.campssg.service.CampingService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camping")
public class CampingController {
    private final CampingService campingService;

    @GetMapping("/{keyword}")
    public ResponseEntity<JSONObject> searchKeyword(@PathVariable String keyword) throws IOException, ParseException {
        return ResponseEntity.ok(campingService.searchKeyword(keyword));
    }

    @GetMapping("/place")
    public ResponseEntity<JSONObject> searchPlace(@RequestBody @Valid CampingRequestDto requestDto) throws IOException, ParseException {
        return ResponseEntity.ok(campingService.searchPlace(requestDto));
    }
}
