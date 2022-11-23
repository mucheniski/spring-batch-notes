package com.springbatch.faturacartaocredito.reader;

import com.springbatch.faturacartaocredito.dominio.CartaoCredito;
import com.springbatch.faturacartaocredito.dominio.Cliente;
import com.springbatch.faturacartaocredito.dominio.Transacao;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
public class TransacaoReaderConfig {

    String sqlSelect =  "  select * from \n" +
                        "  migracao_dados.transacao\n" +
                        "  join migracao_dados.cartao_credito\n" +
                        "  on cartao_credito.numero_cartao_credito = transacao.numero_cartao_credito\n" +
                        "  order by transacao.numero_cartao_credito";

    @Bean
    public JdbcCursorItemReader<Transacao> transacaoReader(
        @Qualifier("aplicacaoDataSource") DataSource aplicacaoDataSource
    ){
        return new JdbcCursorItemReaderBuilder<Transacao>()
                .name("transacaoReader")
                .dataSource(aplicacaoDataSource)
                .sql(sqlSelect)
                .rowMapper(mapeadorCamposTransacao())
                .build();
    }

    private RowMapper<Transacao> mapeadorCamposTransacao() {

        return new RowMapper<Transacao>() {
            @Override
            public Transacao mapRow(ResultSet rs, int rowNum) throws SQLException {

                Cliente cliente = Cliente.builder()
                                        .id(rs.getInt("cliente"))
                                    .build();

                CartaoCredito cartaoCredito = CartaoCredito.builder()
                            .numero(rs.getInt("numero_cartao_credito"))
                            .cliente(cliente)
                        .build();

                Transacao transacao = Transacao.builder()
                                        .id(rs.getInt("id"))
                                        .cartaoCredito(cartaoCredito)
                                        .data(rs.getDate("data"))
                                        .valor(rs.getDouble("valor"))
                                        .descricao(rs.getString("descricao"))
                                    .build();

                return transacao;

            }
        };

    }

}
