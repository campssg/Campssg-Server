package com.campssg;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CampssgApplication {

    @Value("${firebase.sdk.path}")
    private String firebaseSdkPath;

    public static void main(String[] args) {
        SpringApplication.run(CampssgApplication.class, args);
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        Resource resource = new ClassPathResource(firebaseSdkPath);
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(resource.getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions
            .builder()
            .setCredentials(googleCredentials)
            .build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "campssg");
        return FirebaseMessaging.getInstance(app);
    }
}
