package com.example.backend.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.enums.PaymentStatus;
import com.example.backend.enums.SubscriptionStatus;
import com.example.backend.model.Payment;
import com.example.backend.model.SubscriptionPackage;
import com.example.backend.model.User;
import com.example.backend.model.UserSubscription;
import com.example.backend.repository.PaymentRepository;
import com.example.backend.repository.UserSubscriptionRepository;

import brevo.ApiException;

@Service
public class SubscriptionService {

    @Autowired
    private UserSubscriptionRepository subscriptionRepo;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private EmailService emailService;

    public void activateSubscription(User user, SubscriptionPackage pack, String orderId, String paymentId) throws ApiException {

        UserSubscription subscription = new UserSubscription();

        subscription.setUser(user);
        subscription.setSubscriptionPackage(pack);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        LocalDateTime now = LocalDateTime.now();

        subscription.setStartDate(now);
        subscription.setEndDate(calculateEndDate(pack, now));

        subscriptionRepo.save(subscription);

        Payment payment = new Payment();

        payment.setUser(user);
        payment.setSubscriptionPackage(pack);
        payment.setAmount(pack.getPrice());
        payment.setPaymentDate(now);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setRazorpayOrderId(orderId);

        payment.setRazorpayPaymentId(paymentId);

        paymentRepo.save(payment);

        sendSubscriptionConfirmation(user, pack, subscription);

    }

    private LocalDateTime calculateEndDate(SubscriptionPackage pack, LocalDateTime startDate) {

        return switch (pack.getBillingCycle()) {
            case MONTHLY -> startDate.plusMonths(1);
            case ANNUAL -> startDate.plusYears(1);
        };

    }

    public void sendSubscriptionConfirmation(User user, SubscriptionPackage pack, UserSubscription subscription) throws ApiException {

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <title>Subscription Activated – Synapz AI</title>
                    <style>
                        body {
                            margin: 0; padding: 0;
                            background-color: #0d0d0f;
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
                            -webkit-text-size-adjust: 100%%;
                        }
                        table { border-collapse: collapse; }
                        a { color: #a78bfa; text-decoration: none; }
                        a:hover { text-decoration: underline; }
                    </style>
                </head>
                <body>
                    <table width="100%%" cellpadding="0" cellspacing="0" role="presentation"
                        style="background-color: #0d0d0f; padding: 40px 16px;">
                        <tr>
                            <td align="center">

                                <!-- Email Card -->
                                <table width="600" cellpadding="0" cellspacing="0" role="presentation"
                                    style="max-width: 600px; width: 100%%; background-color: #18181b;
                                    border-radius: 16px; border: 1px solid #2e2e38; overflow: hidden;">

                                    <!-- Header -->
                                    <tr>
                                        <td style="padding: 40px 40px 32px; text-align: center;
                                            border-bottom: 1px solid #2e2e38;">
                                            <!-- Logo / Brand -->
                                            <div style="margin-bottom: 24px;">
                                                <span style="display: inline-block; background: linear-gradient(135deg, #7c3aed, #a78bfa);
                                                    border-radius: 12px; padding: 10px 20px;">
                                                    <span style="font-size: 18px; font-weight: 700;
                                                        color: #ffffff; letter-spacing: 0.5px;">
                                                        &#9889; Synapz AI
                                                    </span>
                                                </span>
                                            </div>

                                            <!-- Status Badge -->
                                            <div style="margin-bottom: 16px;">
                                                <span style="display: inline-block; background-color: #14532d;
                                                    color: #86efac; font-size: 12px; font-weight: 600;
                                                    letter-spacing: 1px; text-transform: uppercase;
                                                    padding: 6px 16px; border-radius: 100px;
                                                    border: 1px solid #166534;">
                                                    &#10003;&nbsp; Payment Successful
                                                </span>
                                            </div>

                                            <h1 style="margin: 0 0 8px; font-size: 26px; font-weight: 700;
                                                color: #f4f4f5; letter-spacing: -0.3px;">
                                                Subscription Activated
                                            </h1>
                                            <p style="margin: 0; font-size: 15px; color: #a1a1aa; line-height: 1.5;">
                                                Welcome aboard, <strong style="color: #e4e4e7;">%s</strong>!
                                                Your plan is live and ready to use.
                                            </p>
                                        </td>
                                    </tr>

                                    <!-- Subscription Details -->
                                    <tr>
                                        <td style="padding: 32px 40px;">

                                            <p style="margin: 0 0 20px; font-size: 13px; font-weight: 600;
                                                color: #71717a; letter-spacing: 0.8px; text-transform: uppercase;">
                                                Plan Summary
                                            </p>

                                            <!-- Detail Rows -->
                                            <table width="100%%" cellpadding="0" cellspacing="0" role="presentation">

                                                <!-- Plan -->
                                                <tr>
                                                    <td style="padding: 14px 0; border-bottom: 1px solid #27272a;">
                                                        <table width="100%%" cellpadding="0" cellspacing="0">
                                                            <tr>
                                                                <td style="font-size: 14px; color: #71717a;">Plan</td>
                                                                    <td align="right">
                                                                        <span style="display: inline-block;
                                                                            background-color: #3b0764; color: #c4b5fd;
                                                                            font-size: 13px; font-weight: 600;
                                                                            padding: 4px 12px; border-radius: 6px;
                                                                            border: 1px solid #5b21b6;">
                                                                            %s
                                                                        </span>
                                                                    </td>
                                                                </tr>
                                                        </table>
                                                    </td>
                                                </tr>

                                                <!-- Price -->
                                                <tr>
                                                    <td style="padding: 14px 0; border-bottom: 1px solid #27272a;">
                                                        <table width="100%%" cellpadding="0" cellspacing="0">
                                                            <tr>
                                                                <td style="font-size: 14px; color: #71717a;">Amount Paid</td>
                                                                <td align="right"
                                                                    style="font-size: 18px; font-weight: 700; color: #f4f4f5;">
                                                                    &#8377;%s
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>

                                                <!-- Start Date -->
                                                <tr>
                                                    <td style="padding: 14px 0; border-bottom: 1px solid #27272a;">
                                                        <table width="100%%" cellpadding="0" cellspacing="0">
                                                            <tr>
                                                                <td style="font-size: 14px; color: #71717a;">Start Date</td>
                                                                <td align="right"
                                                                    style="font-size: 14px; color: #e4e4e7; font-weight: 500;">
                                                                    %s
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>

                                                <!-- End Date -->
                                                <tr>
                                                    <td style="padding: 14px 0;">
                                                        <table width="100%%" cellpadding="0" cellspacing="0">
                                                            <tr>
                                                                <td style="font-size: 14px; color: #71717a;">Renewal Date</td>
                                                                <td align="right"
                                                                    style="font-size: 14px; color: #e4e4e7; font-weight: 500;">
                                                                    %s
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>

                                            </table>
                                        </td>
                                    </tr>

                                    <!-- CTA -->
                                    <tr>
                                        <td style="padding: 0 40px 36px; text-align: center;">
                                            <a href="#"
                                                style="display: inline-block; background: linear-gradient(135deg, #7c3aed, #6d28d9);
                                                    color: #ffffff; font-size: 15px; font-weight: 600;
                                                    padding: 14px 36px; border-radius: 10px;
                                                    text-decoration: none; letter-spacing: 0.2px;">
                                                Go to Dashboard &rarr;
                                            </a>
                                        </td>
                                    </tr>

                                    <!-- Divider -->
                                    <tr>
                                        <td style="border-top: 1px solid #2e2e38;"></td>
                                    </tr>

                                    <!-- Footer -->
                                    <tr>
                                        <td style="padding: 24px 40px; text-align: center;">
                                            <p style="margin: 0 0 8px; font-size: 12px; color: #52525b; line-height: 1.6;">
                                                If you did not make this purchase, please
                                                <a href="mailto:support@synapzai.com"
                                                style="color: #a78bfa;">contact support</a> immediately.
                                            </p>
                                            <p style="margin: 0; font-size: 12px; color: #3f3f46;">
                                                &copy; 2025 Synapz AI &bull; All rights reserved
                                            </p>
                                        </td>
                                    </tr>

                                </table>
                                <!-- /Email Card -->

                            </td>
                        </tr>
                    </table>
                </body>
            </html>
        """
        .formatted(
            user.getUsername(),
            pack.getPackageName(),
            pack.getPrice(),
            subscription.getStartDate(),
            subscription.getEndDate()
        );

        emailService.sendEmail(user.getEmail(), "Subscription Activated", html);

    }

}
