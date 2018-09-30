package com.betsapp.batch;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.betsapp.BetsAppProperties;
import com.betsapp.mgr.domain.Match;

@Configuration
@EnableBatchProcessing
public class BetsAppBatch {

    private static final Logger logger = LoggerFactory.getLogger(BetsAppBatch.class);

    @Autowired
    private StepBuilderFactory stepFactory;

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private BetsAppProperties properties;

    @Bean("files")
    public Job importMatchesJob(MatchItemReader reader,
                                MatchItemProcessor processor,
                                MatchItemWriter writer) throws Exception {
    	
        Step s1 = stepFactory.get("import-file-step")
                .<Match, Match>chunk(5)
                .reader(reader.readFileDelimiter(properties.getFileToProcessAsResource()))
                .processor(processor.processBet())
                .writer(writer.matchJDBCWriter(null))
                .build();

        Step s2 = stepFactory.get("rename-file")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                        File oldFile = properties.getFileToProcessAsResource().getFile();
                        File newFile = new File(oldFile.getParent(), "processed." + System.currentTimeMillis());

                        if(oldFile.exists()) {
                            logger.info("File has been renamed " + oldFile.renameTo(newFile));
                        }

                        return RepeatStatus.FINISHED;
                    }
                })
                .build();

        return jobFactory.get("import-matches-job")
                .incrementer(new RunIdIncrementer())
                .start(s1)
                .next(s2)
                .build();
    }

}
