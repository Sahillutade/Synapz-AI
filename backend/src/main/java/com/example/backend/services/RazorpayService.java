package com.example.backend.services;

import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class RazorpayService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    private String secret;

    public Order creatOrder(Double amount) throws RazorpayException {

        JSONObject options = new JSONObject();

        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", UUID.randomUUID().toString());

        return razorpayClient.orders.create(options);

    }

    public boolean verifySignature(String orderId, String paymentId, String signature) {

        try{

            String payload = orderId + "|" + paymentId;

            Mac sha256 = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

            sha256.init(secretKey);

            byte[] hash = sha256.doFinal(payload.getBytes());

            String generated = bytesToHex(hash);

            return generated.equals(signature);

        }
        catch(Exception e){
            return false;
        }

    }

    private String bytesToHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();

        for(byte b : bytes) {

            sb.append(String.format("%02x", b));

        }

        return sb.toString();

    }

}
