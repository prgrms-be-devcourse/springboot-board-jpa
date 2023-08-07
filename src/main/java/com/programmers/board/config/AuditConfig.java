package com.programmers.board.config;

import com.programmers.board.constant.SessionConst;
import com.programmers.board.domain.User;
import com.programmers.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.NoSuchElementException;
import java.util.Objects;

@EnableJpaAuditing
@Configuration
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider(HttpServletRequest httpServletRequest,
                                                UserRepository userRepository) {
        return () -> {
            HttpSession session = httpServletRequest.getSession(false);
            if (Objects.isNull(session)) {
                return java.util.Optional.empty();
            }
            User user = getUser(userRepository, session);
            return java.util.Optional.of(user.getName());
        };
    }

    private User getUser(UserRepository userRepository, HttpSession session) {
        Long loginUserId = (Long) session.getAttribute(SessionConst.LOGIN_USER_ID);
        return userRepository.findById(loginUserId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다"));
    }
}
