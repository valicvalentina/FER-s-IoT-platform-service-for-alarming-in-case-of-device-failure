package com.alarms.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.alarms.demo.DataController;


@Component
@EnableScheduling
public class NotificationScheduler {
    private final MyBot telegramBot;

   
    public NotificationScheduler(MyBot telegramBot) {
        this.telegramBot = telegramBot;
    }
	
   
    public void sendNotification(String chatId, String n) {
        telegramBot.sendNotification(chatId, n);
    }
}





