package com.example.backend.dto;

import java.time.LocalDateTime;

import com.example.backend.enums.MessageRole;

public class MessageResponse {

    private Long id;
    private String content;
    private MessageRole role;
    private LocalDateTime createdAt;
    
    public MessageResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageRole getRole() {
        return role;
    }

    public void setRole(MessageRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public MessageResponse(Long id, String content, MessageRole role, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.role = role;
        this.createdAt = createdAt;
    }

}
