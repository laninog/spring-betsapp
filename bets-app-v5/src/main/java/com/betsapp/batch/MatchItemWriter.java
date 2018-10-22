package com.betsapp.batch;

import com.betsapp.mgr.domain.Match;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Configuration
public class MatchItemWriter {

    @Bean
    public JdbcBatchItemWriter<Match> matchJDBCWriter(DataSource ds) {
        return new JdbcBatchItemWriterBuilder<Match>()
                .dataSource(ds)
                .sql("insert into mgr_matches(local, rel_local, visitor, rel_visitor, rel_draw, open, close) values (?, ?, ?, ?, ?, ?, ?);")
                .itemPreparedStatementSetter(new ItemPreparedStatementSetter<Match>() {
                    @Override
                    public void setValues(Match item, PreparedStatement ps) throws SQLException {

                        ps.setString(1, item.getLocal());
                        ps.setInt(2, item.getRelLocal());
                        ps.setString(3, item.getVisitor());
                        ps.setInt(4, item.getRelVisitor());
                        ps.setInt(5, item.getRelDraw());
                        ps.setTimestamp(6, Timestamp.valueOf(item.getOpen()));
                        ps.setTimestamp(7, Timestamp.valueOf(item.getClose()));

                    }
                }).build();
    }

}
