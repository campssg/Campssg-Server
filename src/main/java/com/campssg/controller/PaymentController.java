package com.campssg.controller;

import com.campssg.dto.ResponseMessage;
import com.campssg.service.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @ApiOperation(value = "결제 검증")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "결제 검증 완료")
    })
    @GetMapping("/{impUid}/{paidAmount}")
    public ResponseEntity<ResponseMessage<Boolean>> martList(@PathVariable("impUid") String impUid,
        @PathVariable("paidAmount") BigDecimal paidAmount)
        throws IOException, IamportResponseException {
        return new ResponseEntity<>(
            ResponseMessage.res(HttpStatus.OK, "마트 조회 성공", paymentService.paymentValidCheck(impUid, paidAmount)),
            HttpStatus.OK);
    }
}
