package com.example.backend;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ChatMessageRequest;
import com.example.backend.dto.ChatMessageResponse;
import com.example.backend.dto.UpdateChatTitleRequest;
import com.example.backend.model.Chat;
import com.example.backend.services.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<?> getChatMessages(Authentication authentication, @PathVariable Long chatId) {

        return ResponseEntity.ok(chatService.getChatMessages(authentication, chatId));

    }

    @PostMapping("/message")
    public ResponseEntity<ChatMessageResponse> sendMessage(Authentication authentication, @ModelAttribute ChatMessageRequest request) throws IOException {

        return ResponseEntity.ok(chatService.sendMessage(authentication, request.getChatId(), request.getMessage(), request.getFiles()));

    }

    @PutMapping("/{chatId}/editTitle")
    public ResponseEntity<?> updateTitle(@PathVariable Long chatId, @RequestBody UpdateChatTitleRequest request) {

        Chat chat = chatService.updateChatTitle(chatId, request.getTitle());

        return ResponseEntity.ok(chat);

    }

    @PutMapping("/{chatId}/pinChat")
    public ResponseEntity<?> togglePin(@PathVariable Long chatId) {

        Chat chat = chatService.togglePinChat(chatId);

        return ResponseEntity.ok(chat);

    }

    @DeleteMapping("/deleteChat/{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable Long chatId) {

        chatService.deleteChat(chatId);

        return ResponseEntity.ok("Chat deleted Successfully");

    }


}
