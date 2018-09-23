package com.betsapp.batch;

import com.betsapp.mgr.domain.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BetsAppBatch {

    private static final Logger logger = LoggerFactory.getLogger(BetsAppBatch.class);

    @Autowired
    private StepBuilderFactory stepFactory;

    @Autowired
    private JobBuilderFactory jobFactory;

    @Value("${betsconf.file-to-process}")
    private Resource in;

    @Bean
    public Job importMatchesJob(MatchItemReader reader,
                                MatchItemProcessor processor,
                                MatchItemWriter writer) throws Exception {

        Step s1 = stepFactory.get("import-file-step")
                .<Match, Match>chunk(5)
                .reader(reader.readFileDelimiter(in))
                .processor(processor.processBet())
                .writer(writer.matchJDBCWriter(null))
                .build();

        return jobFactory.get("import-matches-job")
                .incrementer(new RunIdIncrementer())
                .start(s1)
                .build();
    }

}
