package com.alarms.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyBot extends TelegramLongPollingBot {
	
    public MyBot() {
	
        }
   // @Value("${telegram.bot.token}")
    private String botToken="6239901383:AAHXvXL2e1gaWNZA8Ek8uH-I0vNV2P37kS0";

    
    @Override
    public void onUpdateReceived(Update update) {
    	//koristit cu kasnije za neki update
    }

    @Override
    public String getBotUsername() {
        return "username";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    public void setBotToken(String token) {
        botToken = token;
    }

    public void sendNotification(String chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
