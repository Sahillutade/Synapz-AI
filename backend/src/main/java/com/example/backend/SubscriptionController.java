package com.example.backend;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.CreateOrderRequest;
import com.example.backend.dto.SubscriptionPackageDTO;
import com.example.backend.dto.VerifyPaymentRequest;
import com.example.backend.model.SubscriptionPackage;
import com.example.backend.model.User;
import com.example.backend.repository.SubscriptionPackageRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserSubscriptionRepository;
import com.example.backend.services.RazorpayService;
import com.example.backend.services.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionPackageRepository packageRepository;

    @Autowired
    private UserSubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKey;

    @GetMapping("/packages")
    public List<SubscriptionPackageDTO> getAllPackages() {
        return subscriptionService.getAllPackages();
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(Authentication authentication, @RequestBody CreateOrderRequest request) throws Exception {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        SubscriptionPackage pack = packageRepository.findById(request.getPackageId()).orElseThrow();

        com.razorpay.Order order = razorpayService.creatOrder(pack.getPrice().doubleValue());

        return ResponseEntity.ok(
            Map.of(
                "orderId", order.get("id"),
                "amount", order.get("amount"),
                "key", razorpayKey
            )
        );

    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(Authentication authentication, @RequestBody VerifyPaymentRequest request) throws Exception {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        boolean verified = razorpayService.verifySignature(request.getRazorpayOrderId(), request.getRazorpayPaymentId(), request.getRazorpaySignature());

        if (!verified) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "message",
                    "Payment verification failed"
                )
            );
        }

        SubscriptionPackage pack = packageRepository.findById(request.getPackageId()).orElseThrow();


        subscriptionService.activateSubscription(user, pack, request.getRazorpayOrderId(), request.getRazorpayPaymentId());

        return ResponseEntity.ok(
            Map.of(
                "message",
                "subscription activated"
            )
        );

    }

    public ResponseEntity<?> subscribeFreePackage(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        subscriptionService.activateFreeSubscription(user);

        return ResponseEntity.ok("Free package activated successfully");

    }

}
