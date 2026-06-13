package com.example.backend.dto;

public class SendMessageRequest {

    private Long chatId;
    private String message;

    public SendMessageRequest() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
