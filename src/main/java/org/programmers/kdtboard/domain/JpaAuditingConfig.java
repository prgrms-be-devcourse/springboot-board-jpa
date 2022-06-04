package org.programmers.kdtboard.domain;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
	// security 사용시 사용자 정보를 넣어 주는 부분이라 비웠습니다.
}