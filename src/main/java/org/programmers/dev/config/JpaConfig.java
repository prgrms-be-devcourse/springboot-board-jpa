package org.programmers.dev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    // 추가적인 JPA 관련 구성이 필요한 경우 여기에 작성
}
