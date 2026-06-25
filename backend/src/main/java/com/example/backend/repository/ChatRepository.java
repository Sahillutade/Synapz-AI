package com.example.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Chat;
import com.example.backend.model.User;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByUserOrderByUpdatedAtDesc(User user);

    List<Chat> findByUserOrderByPinnedDescUpdatedAtDesc(User user);

    List<Chat> findByUser(User user);

}
