package com.programmers.springboard.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeactivateMemberScheduler {
	private final JobLauncher jobLauncher;
	private final Job deactivateMemberJob;

	@Scheduled(cron = "0 0 2 * * ?") // 매일 새벽 2시에 실행
	public void runJob() {
		try {
			jobLauncher.run(deactivateMemberJob, new JobParameters());
		} catch (JobExecutionException e) {
			log.warn("DeactivateMemberScheduler error : ", e);
		}
	}
}
