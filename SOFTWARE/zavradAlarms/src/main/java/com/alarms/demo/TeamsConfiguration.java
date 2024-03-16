package com.alarms.demo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TeamsConfiguration {
 private String webhookUrl="https://ferhr.webhook.office.com/webhookb2/1e8c14ec-a33b-494a-ab60-53390655714d@ca71eddc-cc7b-4e5b-95bd-55b658e696be/IncomingWebhook/eab3dc0cffb7497ea6908093517fd0ba/ead2ed2e-d921-4d63-931d-47a2644ddc8e";

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

