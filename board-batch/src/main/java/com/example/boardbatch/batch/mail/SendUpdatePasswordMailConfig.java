package com.example.boardbatch.batch.mail;

import static com.example.boardbatch.batch.util.SqlSupport.createUpdatePasswordMailQuery;

import com.example.board.domain.email.service.MailService;
import com.example.board.domain.member.entity.Member;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SendUpdatePasswordMailConfig {

    public static final Integer CHUNK_SIZE = 5;

    private final EntityManagerFactory entityManagerFactory;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManagerFactory;
    private final MailService mailService;

    @Bean
    public Job SendUpdatePasswordMailJob() {
        return new JobBuilder("sendUpdatePasswordMail", jobRepository)
            .start(sendUpdatePasswordMailStep())
            .build();
    }

    @Bean
    public Step sendUpdatePasswordMailStep() {
        log.info("execute Step");
        return new StepBuilder("sendUpdatePasswordMailStep", jobRepository)
            .<Member, Member>chunk(CHUNK_SIZE, transactionManagerFactory)
            .reader(memberItemReader())
            .writer(memberItemWriter())
            .build();
    }

    @Bean
    public ItemReader<Member> memberItemReader() {
        log.info("execute Reader");
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);

        return new JpaPagingItemReaderBuilder<Member>()
            .name("memberItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString(createUpdatePasswordMailQuery())
            .parameterValues(Collections.singletonMap("sixMonthsAgo", sixMonthsAgo))
            .pageSize(CHUNK_SIZE)
            .build();
    }

    @Bean
    public ItemWriter<Member> memberItemWriter() {
        log.info("execute Writer");
        return items -> {
            for (Member member : items) {
                mailService.sendMail(member.getEmail(), "UPDATE-PASSWORD");
            }
        };
    }
}
