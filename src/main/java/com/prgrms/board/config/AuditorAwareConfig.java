package com.prgrms.board.config;

import com.prgrms.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareConfig implements AuditorAware<String> {
    private final HttpSession httpSession;

    @Override
    public Optional<String> getCurrentAuditor() {
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null)
            return null;

        return Optional.ofNullable(member.getName());
    }
}
