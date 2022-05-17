package com.kdt.springbootboardjpa.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@TestPropertySource
@ComponentScan(
        basePackages = {"com.kdt.springbootboardjpa"},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = {Configuration.class}
                )
        }
)
public class TestConfig {
}
