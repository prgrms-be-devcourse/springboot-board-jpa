package com.example.board.service;

import com.example.board.jwt.JwtAuthToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthService {

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthToken) {
            return ((JwtAuthToken) authentication).getUserId();
        }
        return null;
    }

    public List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthToken) {
            return ((JwtAuthToken) authentication).getRoles();
        }
        return Collections.emptyList();
    }
}
