package com.campssg.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class ResponseMessage<T> {

    @ApiModelProperty(position = 1, required = true, value = "응답 상태 코드")
    private HttpStatus statusCode;

    @ApiModelProperty(position = 2, required = true, value = "응답 메시지")
    private String message;

    @ApiModelProperty(position = 3, required = true, value = "데이터")
    private T data;

    public ResponseMessage(final HttpStatus statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }

    public static <T> ResponseMessage<T> res(final HttpStatus statusCode, final String message) {
        return res(statusCode, message, null);
    }

    public static <T> ResponseMessage<T> res(final HttpStatus statusCode, final String message,
        final T t) {
        return ResponseMessage.<T>builder()
            .data(t)
            .statusCode(statusCode)
            .message(message)
            .build();
    }
}
