package com.example.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GeminiService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    public String generateContent(String prompt, List<MultipartFile> files) {

        try {

            List<Map<String, Object>> requestParts = new ArrayList<>();

            if(prompt != null && !prompt.isBlank()) {

                requestParts.add(
                    Map.of(
                        "text",
                        prompt
                    )
                );

            }

            if(files != null && !files.isEmpty()) {

                for(MultipartFile file : files) {

                    String base64 = java.util.Base64.getEncoder().encodeToString(file.getBytes());

                    requestParts.add(
                        Map.of(
                            "inline_data",
                            Map.of(
                                "mime_type",
                                file.getContentType(),
                                "data",
                                base64
                            )
                        )
                    );

                } 

            }

            boolean hasImage = files != null && !files.isEmpty();

            if (hasImage && (prompt == null || prompt.isBlank())) {
                
                requestParts.add(
                    0,
                    Map.of(
                        "text",
                        "Describe this image in detail."
                    )
                );

            }

            if(requestParts.isEmpty()) {

                throw new RuntimeException("Either message or at least one file must be provided.");

            }

            String url = "https://generativelanguage.googleapis.com/v1beta/models/"
            + model
            + ":generateContent?key="
            + apiKey;

            Map<String, Object> body = 
            Map.of(
                "contents",
                List.of(
                    Map.of(
                        "parts",
                        requestParts
                    )
                )
            );

            Map<?, ?> response = null;

            int maxRetries = 3;

            for (int attempt = 1; attempt <= maxRetries; attempt++) {

                try {

                    response = restTemplate.postForObject(url, body, Map.class);

                    break;

                }
                catch (HttpServerErrorException.ServiceUnavailable e) {

                    if(attempt == maxRetries) {

                        return "The AI service is currently experiencing high demand. Please try again in a few moments.";

                    }

                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                }

            }

            if (response == null) {
                throw new RuntimeException("No response from Gemini");
            }

            List<?> candidates = (List<?>) response.get("candidates");

            if(candidates == null || candidates.isEmpty()) {

                throw new RuntimeException("Gemini returned no candidates");

            }

            Map<?, ?> candidate = (Map<?, ?>) candidates.get(0);

            Map<?, ?> content = (Map<?, ?>) candidate.get("content");

            List<?> responseParts = (List<?>) content.get("parts");

            Map<?, ?> firstPart = (Map<?, ?>) responseParts.get(0);

            return firstPart.get("text").toString();

        }
        catch(Exception e){
            throw new RuntimeException(
                "Gemini API error: " + e.getMessage(), e
            );

        }

    }

    public String generateChatTitle(String userMessage, String aiResponse) {

        String prompt = """
            Generate a short title for this conversation.

            Rules:
            - Maximum 5 words
            - No quotes
            - No punctuation
            - Return only the title

            User:
            %s

            Assistant:
            %s
        """
        .formatted(userMessage == null ? "" : userMessage, aiResponse);

        return generateContent(prompt, null).trim();

    }

}
