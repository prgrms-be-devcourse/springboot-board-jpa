package com.programmers.springboard.config;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.repository.MemberRepository;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class DeactivateMemberJobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final EntityManagerFactory entityManagerFactory;
	private final MemberRepository memberRepository;

	@Bean
	public Job deactivateMemberJob() {
		return new JobBuilder("deactivateMemberJob", jobRepository)
			.start(step())
			.build();
	}

	@Bean
	public Step step() {
		return new StepBuilder("csv-step", jobRepository)
			.<Member, Member>chunk(10, transactionManager)
			.reader(deactivateMemberReader())
			.processor(deactivateMemberProcessor())
			.writer(deactivateMemberWriter())
			.build();
	}

	@Bean
	public ItemReader<Member> deactivateMemberReader() {
		LocalDateTime aYearAgo = LocalDateTime.now().minusYears(1);
		return new JpaPagingItemReaderBuilder<Member>()
			.name("deactivateMemberReader")
			.entityManagerFactory(entityManagerFactory)
			.queryString("SELECT m FROM Member m WHERE m.lastLoginDate < :aYearAgo")
			.parameterValues(Collections.singletonMap("aYearAgo", aYearAgo))
			.pageSize(10)
			.build();
	}

	@Bean
	public ItemProcessor<Member, Member> deactivateMemberProcessor() {
		return member -> {
			member.deactivate();
			return member;
		};
	}

	@Bean
	public ItemWriter<Member> deactivateMemberWriter() {
		return memberRepository::saveAll;
	}
}
