package com.campssg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CampssgApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampssgApplication.class, args);
    }

}
