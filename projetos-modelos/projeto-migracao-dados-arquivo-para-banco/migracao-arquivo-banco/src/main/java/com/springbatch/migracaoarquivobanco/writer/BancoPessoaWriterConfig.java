package com.springbatch.migracaoarquivobanco.writer;

import com.springbatch.migracaoarquivobanco.domain.Pessoa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Configuration
public class BancoPessoaWriterConfig {

    String sqlInsert = "INSERT INTO migracao_dados.pessoa\n" +
            "(id, nome, email, data_nascimento, idade)\n" +
            "VALUES(?,?,?,?,?);\n";

    @Bean
    public JdbcBatchItemWriter<Pessoa> bancoPessoaWriter(@Qualifier("aplicacaoDataSource")DataSource aplicacaoDataSource) {
        return new JdbcBatchItemWriterBuilder<Pessoa>()
                .dataSource(aplicacaoDataSource)
                .sql(sqlInsert)
                .itemPreparedStatementSetter(setarCampos())
                .build();
    }

    private ItemPreparedStatementSetter<Pessoa> setarCampos() {

        return new ItemPreparedStatementSetter<Pessoa>() {
            @Override
            public void setValues(Pessoa pessoa, PreparedStatement ps) throws SQLException {
                ps.setInt(1, pessoa.getId());
                ps.setString(2, pessoa.getNome());
                ps.setString(3, pessoa.getEmail());
                ps.setString(4, pessoa.getDataNascimento());
                ps.setInt(5, pessoa.getIdade());
            }
        };

    }
//
//    private static Date convertStringToDate(String dataInformada) {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//        try {
//            return dateFormat.parse(dataInformada);
//        } catch (ParseException e) {
//            log.error("Erro ao converter a data: ", e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }

}
