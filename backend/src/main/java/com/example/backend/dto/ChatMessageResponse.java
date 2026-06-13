package com.example.backend.dto;

public class ChatMessageResponse {

    private Long chatId;
    private String chatTitle;
    private String assistantResponse;
    
    public ChatMessageResponse() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getChatTitle() {
        return chatTitle;
    }

    public void setChatTitle(String chatTitle) {
        this.chatTitle = chatTitle;
    }

    public String getAssistantResponse() {
        return assistantResponse;
    }

    public void setAssistantResponse(String assistantResponse) {
        this.assistantResponse = assistantResponse;
    }

    public ChatMessageResponse(Long chatId, String chatTitle, String assistantResponse) {
        this.chatId = chatId;
        this.chatTitle = chatTitle;
        this.assistantResponse = assistantResponse;
    }

}
