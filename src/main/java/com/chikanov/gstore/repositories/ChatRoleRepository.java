package com.chikanov.gstore.repositories;

import com.chikanov.gstore.entity.ChatRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoleRepository extends JpaRepository<ChatRole, Long> {
}
