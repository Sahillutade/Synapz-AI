package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.enums.SubscriptionStatus;
import com.example.backend.model.User;
import com.example.backend.model.UserSubscription;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findByStatus(SubscriptionStatus status);

    UserSubscription findTopByUserAndStatusOrderByIdDesc(User user, SubscriptionStatus status);

    Optional<UserSubscription> findByUser(User user);

    Optional<UserSubscription> findByUserAndStatus(User user, SubscriptionStatus status);

}
