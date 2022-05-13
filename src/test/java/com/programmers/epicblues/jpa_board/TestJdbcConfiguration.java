package com.programmers.epicblues.jpa_board;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestJdbcConfiguration {

  private final static JdbcDatabaseContainer databaseContainer = new MySQLContainer<>(
      DockerImageName
          .parse("mysql:8.0.28-debian"))
      .withDatabaseName("board")
      .withCommand("--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci");

  static {
    databaseContainer.start();
  }

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .username(databaseContainer.getUsername())
        .password(databaseContainer.getPassword())
        .url(databaseContainer.getJdbcUrl())
        .build();
  }
}
