package com.campssg.dto.push;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@ApiModel("FCM 알림 요청")
public class PushMessage {
    private String title;
    private String body;

    public PushMessage(String title, String body){
        this.title = title;
        this.body = body;
    }
}
