package com.alarms.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@RestController
@Component
public class TeamsController {
    @Autowired
    private TeamsConfiguration teamsConfiguration;

    @Autowired
    private RestTemplate restTemplate;

 //   @Scheduled(fixedDelay = 10000)
    public void sendNotificationScheduled(String message) {
        String payload = "{\"text\": \"" + message + "\"}";

        restTemplate.postForEntity(teamsConfiguration.getWebhookUrl(), payload, String.class);
    }
}

