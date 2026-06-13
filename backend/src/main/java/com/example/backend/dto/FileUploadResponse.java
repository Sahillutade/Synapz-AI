package com.example.backend.dto;

public class FileUploadResponse {

    private String imageUrl;
    private String imagePath;
    
    public FileUploadResponse() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public FileUploadResponse(String imageUrl, String imagePath) {
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
    }

}
