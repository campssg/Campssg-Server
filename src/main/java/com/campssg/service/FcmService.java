package com.campssg.service;

import com.campssg.dto.push.PushMessage;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendMulticast(PushMessage message, List<String> tokens) throws FirebaseMessagingException {
        Notification notification = Notification
            .builder()
            .setTitle(message.getTitle())
            .setBody(message.getBody())
            .build();

        MulticastMessage multicastMessage = MulticastMessage.builder()
            .setNotification(notification)
            .addAllTokens(tokens)
            .setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setSound("default").build()).build())
            .build();

        BatchResponse response = firebaseMessaging.sendMulticast(multicastMessage);
        log.info(response.getSuccessCount() + " messages were sent successfully");
    }


}
