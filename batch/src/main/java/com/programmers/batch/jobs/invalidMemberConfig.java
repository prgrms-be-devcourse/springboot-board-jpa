package com.programmers.batch.jobs;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class invalidMemberConfig {

    private final MemberRepository memberRepository;

    @Bean
    public Job invalidMemberJob(JobRepository jobRepository, Step invalidMemberStep){
        return new JobBuilder("invalidMemberJob", jobRepository)
                .start(invalidMemberStep)
                .build();
    }

    @Bean
    public Step invalidMemberStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        log.info(">>>>> invalidMemberJobStep");

        return new StepBuilder("invalidMemberStep", jobRepository)
                .<Member, Member>chunk(10, platformTransactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Member> itemWriter() {
        log.info(">>>>> ListItemWriter");

        return memberRepository::saveAll;
    }

    @Bean
    @StepScope
    public ItemProcessor<Member, Member> itemProcessor() {
        log.info(">>>>> ListItemProcessor");

        return member ->{
            member.changeMemberStatusInActive();
            return member;
        };
    }

    @Bean
    @StepScope
    public ItemReader<Member> itemReader() {
        log.info(">>>>> ListItemReader");
        List<Member> members = memberRepository.findAllByLoginAtBefore(LocalDateTime.now().minusYears(1L));
        log.info(">>>>> InActive Member size {}", members.size());
        return new ListItemReader<>(members);
    }

}
