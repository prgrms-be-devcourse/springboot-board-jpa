package com.programmers.springboard.global.config;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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

import com.programmers.springboard.member.domain.Member;
import com.programmers.springboard.member.repository.MemberRepository;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DeactivateMemberJobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final EntityManagerFactory entityManagerFactory;
	private final MemberRepository memberRepository;

	/**
	 * 배치 작업 정의
	 * @return Job
	 */
	@Bean
	public Job deactivateMemberJob() {
		return new JobBuilder("deactivateMemberJob", jobRepository)
			.start(step())
			.build();
	}

	/**
	 * 배치 작업 단계 정의
	 * @return Step
	 */
	@Bean
	public Step step() {
		return new StepBuilder("csv-step", jobRepository)
			.<Member, Member>chunk(10, transactionManager)
			.reader(deactivateMemberReader())
			.processor(deactivateMemberProcessor())
			.writer(deactivateMemberWriter())
			.build();
	}

	/**
	 * 최근 1년동안 로그인하지 않은 사용자를 조회
	 * @return ItemReader
	 */
	@Bean
	public ItemReader<Member> deactivateMemberReader() {
		LocalDateTime aYearAgo = LocalDateTime.now().minusYears(1);
		return new JpaPagingItemReaderBuilder<Member>()
			.name("deactivateMemberReader")
			.entityManagerFactory(entityManagerFactory)
			.queryString("SELECT m FROM Member m WHERE m.lastLoginDate < :aYearAgo AND m.isActivated = true")
			.parameterValues(Collections.singletonMap("aYearAgo", aYearAgo))
			.pageSize(10)
			.build();
	}

	/**
	 * 읽어온 Member를 비활성화 처리
	 * @return ItemProcessor
	 */
	@Bean
	public ItemProcessor<Member, Member> deactivateMemberProcessor() {
		return member -> {
			member.deactivate();
			return member;
		};
	}

	/**
	 * 처리된 Member 객체를 저장, 데이터베이스에 반영
	 * @return ItemWriter
	 */
	@Bean
	public ItemWriter<Member> deactivateMemberWriter() {
		return memberRepository::saveAll;
	}
}
