package com.betsapp.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BetsAppScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BetsAppScheduler.class);

    @Autowired
    private JobLauncher launcher;

    @Autowired
    @Qualifier("files")
    private Job importBetsJob;

    @Scheduled(cron = "0 */1 * * * *")
    public void executeJob() {

        JobParameters params = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();

        try {
            JobExecution execution = launcher.run(importBetsJob, params);

            logger.info("Job ID ".concat(execution.getJobId().toString()));
            logger.info("Started at ".concat(execution.getStartTime().toString()));

        } catch (Exception ex) {
            logger.error("Error running job", ex);
        }

    }

}
