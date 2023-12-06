package com.example.board.domain.role.repository;

import com.example.board.domain.role.entity.Role;
import com.example.board.domain.role.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
