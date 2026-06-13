package com.example.backend.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.ChatMessageResponse;
import com.example.backend.dto.MessageResponse;
import com.example.backend.enums.MessageRole;
import com.example.backend.model.Attachment;
import com.example.backend.model.Chat;
import com.example.backend.model.Message;
import com.example.backend.model.User;
import com.example.backend.repository.AttachmentRepository;
import com.example.backend.repository.ChatRepository;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private GeminiService geminiService;

    @Transactional
    public ChatMessageResponse sendMessage(Authentication authentication, Long chatId, String message, List<MultipartFile> files) throws IOException {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Chat chat;

        boolean isNewChat = false;

        if(chatId == null) {

            chat = new Chat();
            chat.setUser(user);
            chat.setTitle("New Chat");

            chat = chatRepository.save(chat);

            isNewChat = true;

        }
        else {

            chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));

        }

        Message userMessage = new Message();
        userMessage.setChat(chat);
        userMessage.setRole(MessageRole.USER);
        userMessage.setContent(message == null ? "" : message);

        userMessage = messageRepository.save(userMessage);

        if(files != null && !files.isEmpty()) {

            for(MultipartFile file : files) {

                Attachment attachment = new Attachment();

                attachment.setMessage(userMessage);
                attachment.setFileName(file.getOriginalFilename());
                attachment.setFileType(file.getContentType());
                attachment.setFileSize(file.getSize());
                attachment.setFileData(file.getBytes());
                
                attachmentRepository.save(attachment);

            }

        }

        String aiResponse = geminiService.generateContent(message, files);

        Message assistantMessage = new Message();
        assistantMessage.setChat(chat);
        assistantMessage.setRole(MessageRole.ASSISTANT);
        assistantMessage.setContent(aiResponse);

        messageRepository.save(assistantMessage);

        if(isNewChat) {

            String title = geminiService.generateChatTitle(message, aiResponse);
            chat.setTitle(title);

            chatRepository.save(chat);

        }

        return new ChatMessageResponse(chat.getId(), chat.getTitle(), aiResponse);

    }

    public Chat updateChatTitle(Long chatId, String title) {

        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));

        chat.setTitle(title);

        return chatRepository.save(chat);

    }

    public Chat togglePinChat(Long chatId) {

        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));

        chat.setPinned(!chat.getPinned());
        
        return chatRepository.save(chat);

    }

    public void deleteChat(Long chatId) {

        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));

        chatRepository.delete(chat);

    }

    public List<MessageResponse> getChatMessages(Authentication authentication, Long chatId) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));

        if(!chat.getUser().getId().equals(user.getId())) {

            throw new RuntimeException("Unauthorized access");

        }

        List<Message> messages = messageRepository.findByChatOrderByCreatedAtAsc(chat);
        
        return messages.stream().map(msg -> new MessageResponse(
            msg.getId(),
            msg.getContent(),
            msg.getRole(),
            msg.getCreatedAt()
        ))
        .toList();

    }

}
