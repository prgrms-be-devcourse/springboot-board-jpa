package com.prgrms.board.config;

import com.prgrms.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.prgrms.board.service.PostServiceImpl.SESSION_MEMBER;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class AuditorAwareConfig{
    private final HttpSession httpSession;

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Member member = (Member) httpSession.getAttribute(SESSION_MEMBER);
            if (member == null)
                return null;


            return Optional.ofNullable(member.getName());
        };
    }

}
