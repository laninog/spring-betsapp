package com.betsapp.batch;

import com.betsapp.mgr.domain.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MatchItemProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MatchItemProcessor.class);

    @Bean
    public ItemProcessor<Match, Match> processBet() {
        return new ItemProcessor<Match, Match>() {

            private Long id;

            @BeforeStep
            public void beforeStep(StepExecution stepExecution) {
                id = stepExecution.getJobExecutionId();
            }

            @Override
            public Match process(Match item) throws Exception {

                logger.info("--> Match " + item + ", ID Job " + id);

                return item;
            }
        };
    }

}
