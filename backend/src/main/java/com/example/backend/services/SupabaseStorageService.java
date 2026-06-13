package com.example.backend.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.FileUploadResponse;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.service.key}")
    private String serviceKey;

    @Value("${supabase.bucket}")
    private String bucketName;

    private final RestTemplate restTemplate = new RestTemplate();

    public FileUploadResponse uploadImage(MultipartFile file) throws IOException {

        String extension = "";

        String originalFilename = file.getOriginalFilename();

        if(originalFilename != null && originalFilename.contains(".")) {

            extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        }

        String fileName = UUID.randomUUID().toString() + extension;

        String filePath = "users/" + fileName;

        String uploadUrl = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + filePath;

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(serviceKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> request = new HttpEntity<>(file.getBytes(), headers);

        restTemplate.exchange(uploadUrl, HttpMethod.POST, request, String.class);

        String publicUrl = supabaseUrl + "storage/v1/object/public/" + bucketName + "/" + filePath;

        return new FileUploadResponse(publicUrl, filePath);

    }

    public void deleteImage(String filePath) {

        if(filePath == null || filePath.isBlank()) {
            return;
        }

        String deleteUrl = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + filePath;

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(serviceKey);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        restTemplate.exchange(deleteUrl, HttpMethod.DELETE, request, String.class);

    }

}
