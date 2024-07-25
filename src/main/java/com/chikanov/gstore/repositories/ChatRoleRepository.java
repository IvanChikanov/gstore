package com.chikanov.gstore.repositories;

import com.chikanov.gstore.entity.ChatRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoleRepository extends JpaRepository<ChatRoles, Long> {
}
