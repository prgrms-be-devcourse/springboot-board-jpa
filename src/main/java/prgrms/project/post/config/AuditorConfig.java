package prgrms.project.post.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Component
@EnableJpaAuditing
public class AuditorConfig implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        return Optional.of(randomUUID());
    }
}
