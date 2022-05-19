package com.programmers.epicblues.board.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormatConfiguration {

  // TODO 1: PR 질문
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> builder.serializers(
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
  }
}
