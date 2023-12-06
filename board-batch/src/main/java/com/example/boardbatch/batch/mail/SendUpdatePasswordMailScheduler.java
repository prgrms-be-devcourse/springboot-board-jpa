package com.example.boardbatch.batch.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class SendUpdatePasswordMailScheduler {

    private final Job sendUpdatePasswordMailJob;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 0 * * ?")
    public void runSendUpdatePasswordMailJob() {
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters();

        try {
            jobLauncher.run(sendUpdatePasswordMailJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            log.error("이미 실행 중인 작업입니다.", e);
        } catch (JobRestartException e) {
            log.error("작업 재시작이 허용되지 않습니다.", e);
        } catch (JobInstanceAlreadyCompleteException e) {
            log.error("작업 인스턴스가 이미 완료되었습니다.", e);
        } catch (JobParametersInvalidException e) {
            log.error("유효하지 않은 작업 매개변수입니다.", e);
        }
    }
}
