package com.campssg.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.PaymentBalance;
import java.io.IOException;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private IamportClient iamportClient;

    public PaymentService(@Value("${iamport.apiKey}") String apiKey, @Value("${iamport.apiSecret}") String apiSecret) {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    public boolean paymentValidCheck(String impUid, BigDecimal paidAmount) throws IOException, IamportResponseException {
        IamportResponse<PaymentBalance> response = iamportClient.paymentBalanceByImpUid(impUid);

        return response.getResponse().getAmount().equals(paidAmount);
    }
}
