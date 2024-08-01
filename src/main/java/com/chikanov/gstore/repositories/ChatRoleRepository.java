package com.chikanov.gstore.repositories;

import com.chikanov.gstore.entity.ChatEntity;
import com.chikanov.gstore.entity.ChatRole;
import com.chikanov.gstore.entity.User;
import com.chikanov.gstore.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoleRepository extends JpaRepository<ChatRole, Long> {

    @Query("SELECT cr.chat FROM ChatRole cr WHERE cr.role=:role AND cr.user=:user")
    List<ChatEntity> getChats(@Param("user") User userID, @Param("role")Role role);
}
