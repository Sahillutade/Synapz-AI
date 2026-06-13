package com.example.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import brevo.ApiException;
import brevoApi.TransactionalEmailsApi;
import brevoModel.SendSmtpEmail;
import brevoModel.SendSmtpEmailSender;
import brevoModel.SendSmtpEmailTo;

@Service
public class EmailService {

    private final TransactionalEmailsApi emailApi;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    public EmailService(
        TransactionalEmailsApi emailApi
    ) {
        this.emailApi = emailApi;
    }

    public void sendEmail(
        String to,
        String subject,
        String htmlContent
    ) throws ApiException {

        try {
            SendSmtpEmail email = new SendSmtpEmail();

            email.setSender(new SendSmtpEmailSender()
                .email(senderEmail)
                .name(senderName)
            );
                            
            email.setTo(
                List.of(
                    new SendSmtpEmailTo().email(to)
                )
            );

            email.setSubject(subject);

            email.setHtmlContent(htmlContent);

            emailApi.sendTransacEmail(email);
        }
        catch(ApiException e){
            System.out.println(e.getResponseBody());
            throw e;
        }

    }

    

}
