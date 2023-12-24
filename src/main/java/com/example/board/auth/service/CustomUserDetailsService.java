package com.example.board.auth.service;

import com.example.board.auth.domain.CustomUserDetails;
import com.example.board.domain.User;
import com.example.board.dto.response.CustomResponseStatus;
import com.example.board.exception.CustomException;
import com.example.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        User user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new CustomException(CustomResponseStatus.USER_NOT_FOUND));

        return CustomUserDetails.builder()
                .id(id)
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())))
                .build();
    }
}
