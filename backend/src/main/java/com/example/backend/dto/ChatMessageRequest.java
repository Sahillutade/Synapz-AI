package com.example.backend.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ChatMessageRequest {

    private Long chatId;
    private String message;
    private List<MultipartFile> files;

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
    public List<MultipartFile> getFiles() {
        return files;
    }
    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

}
