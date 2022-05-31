package org.prgrms.kdt.configuration;

import java.util.Optional;
import javax.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.dto.UserDto.CurrentUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleAuditAware implements AuditorAware<String> {

  private final ServletContext context;

  @Override
  public Optional<String> getCurrentAuditor() {
    CurrentUser user = (CurrentUser) context.getAttribute("currentUser");

    return Optional.of(user.name());
  }
}