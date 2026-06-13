package com.example.backend.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.backend.enums.SubscriptionStatus;
import com.example.backend.model.UserSubscription;
import com.example.backend.repository.UserSubscriptionRepository;

@Component
public class SubscriptionScheduler {

    @Autowired
    private UserSubscriptionRepository subscriptionRepository;

    @Scheduled(fixedRate = 60000)
    public void expireSubscription() {

        List<UserSubscription> subscriptions = subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE);

        LocalDateTime now = LocalDateTime.now();

        for (UserSubscription subscription : subscriptions) {

            if(subscription.getEndDate().isBefore(now)) {

                subscription.setStatus(SubscriptionStatus.EXPIRED);

                subscriptionRepository.save(subscription);

            }

        }

    }

}
