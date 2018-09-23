package com.betsapp.batch;

import com.betsapp.mgr.domain.Match;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;

@Configuration
public class MatchItemReader {

    private static final String[] NAMES = { "local", "rellocal", "visitor", "relvisitor", "reldraw", "open", "close" };

    @Bean
    public FlatFileItemReader<Match> readFileDelimiter(Resource in) throws Exception {
        return new FlatFileItemReaderBuilder<Match>()
                .name("file-reader")
                .resource(in)
                .targetType(Match.class)
                .linesToSkip(1)
                .lineMapper(new LineMapper<Match>() {
                    @Override
                    public Match mapLine(String line, int lineNumber) throws Exception {
                        String[] fields = line.split(",");

                        Match tmp = new Match();
                        tmp.setLocal(fields[0]);
                        tmp.setRelLocal(new Integer(fields[1]));
                        tmp.setVisitor(fields[2]);
                        tmp.setRelVisitor(new Integer(fields[3]));
                        tmp.setRelDraw(new Integer(fields[4]));

                        tmp.setOpen(LocalDateTime.parse(fields[5]));
                        tmp.setClose(LocalDateTime.parse(fields[6]));

                        return tmp;
                    }
                })
                .build();
    }



}
