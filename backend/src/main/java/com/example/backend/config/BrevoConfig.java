package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brevo.ApiClient;
import brevoApi.TransactionalEmailsApi;

@Configuration
public class BrevoConfig {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Bean
    public TransactionalEmailsApi transactionalEmailsApi(){

        ApiClient apiClient = brevo.Configuration.getDefaultApiClient();

        apiClient.setApiKey(apiKey);

        return new TransactionalEmailsApi(apiClient);

    }

}
