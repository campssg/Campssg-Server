package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.dto.push.PushMessage;
import com.campssg.service.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/fcm")
public class FcmController {

    private final FcmService fcmService;

    @ApiOperation(value = "fcm test")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "fcm test")
    })
    @PostMapping
    public ResponseEntity<ResponseMessage> saveProductToMart(
        @RequestBody PushMessage request) throws IOException, FirebaseMessagingException {
        fcmService.sendMulticast(request, Arrays.asList(
            "fUpOwRyrSLOKkLTm58o_AD:APA91bFSMH8d2v4_gf2azRZm3CR7PeCzj1sKnuUParxWQ2a0ppMa4SuhbV5M21YSd6Y_6VFo7HVZFzODVXBJ3qrQaqJaDII-qx9Ysc1IGMFXX8oX-REdQZSqTmjskWgfqTPAJa_AV0VQ"));
        return new ResponseEntity<>(ResponseMessage.res(HttpStatus.OK, "해당 마트에 상품 등록", null), HttpStatus.OK);
    }
}
