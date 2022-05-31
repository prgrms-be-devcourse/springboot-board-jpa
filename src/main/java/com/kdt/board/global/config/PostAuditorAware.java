package com.kdt.board.global.config;

import com.kdt.board.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // 구현 필요
        return Optional.empty();
    }
}
