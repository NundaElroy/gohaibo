package com.gohaibo.gohaibo.repo;

import com.gohaibo.gohaibo.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepo extends JpaRepository<Chat, Long> {
}
