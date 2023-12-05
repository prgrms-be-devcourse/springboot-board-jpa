package com.example.board.global.batch.mail;

import com.example.board.domain.email.service.MailService;
import com.example.board.domain.member.entity.Member;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class SendUpdatePasswordMailConfig {

    public static final Integer CHUNK_SIZE = 5;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManagerFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final MailService mailService;

    @Bean
    public Job SendUpdatePasswordMailJob() {
        return new JobBuilder("sendUpdatePasswordMail", jobRepository)
            .start(sendUpdatePasswordMailStep())
            .build();
    }

    @JobScope
    @Bean
    public Step sendUpdatePasswordMailStep() {
        return new StepBuilder("sendUpdatePasswordMailStep", jobRepository)
            .<Member, Void>chunk(CHUNK_SIZE, transactionManagerFactory)
            .reader(memberItemReader())
            .processor(memberItemProcessor())
            .writer(memberItemWriter())
            .build();
    }

    @StepScope
    @Bean
    public ItemReader<Member> memberItemReader() {
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        String query = "SELECT m FROM Member m WHERE m.lastUpdatedPassword <= :sixMonthsAgo and is_deleted = false";
        return new JpaPagingItemReaderBuilder<Member>()
            .name("memberItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString(query)
            .parameterValues(Collections.singletonMap("sixMonthsAgo", sixMonthsAgo))
            .pageSize(10)
            .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Member, Void> memberItemProcessor() {
        return member -> {
            mailService.sendMail(member.getEmail(), "UPDATE-PASSWORD");
            return null;
        };
    }

    @StepScope
    @Bean
    public ItemWriter<Void> memberItemWriter() {
        return items -> {};
    }
}
