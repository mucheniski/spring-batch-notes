package com.springbatch.demonstrativoorcamentario.writer;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.List;

@Component
public class DemonstrativoOrcamentarioRodape implements FlatFileFooterCallback {

    private Double totalGeral = 0.0;

    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.append(String.format("\t\t\t\t\t\t\t  Total: %s\n", NumberFormat.getCurrencyInstance().format(totalGeral)));
        writer.append(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s\n", "fkyew6868fewjfhjjewf"));
    }

    /*
     Esse é um listener do spring batch que vai ficar ouvindo o objeto informado, no caso uma lista de GrupoLancamento
     sempre tem uma lista do objeto que eu estou escrevendo
     para que possa ser tomada uma ação, como calcular o total geral do rodapé por exemplo.
     OBS: Esse método com essa anotação precisa ser registrado no step
     */
    @BeforeWrite
    public void calculaTotalGeralListener(List<GrupoLancamento> gruposLancamentos) {
        for(GrupoLancamento grupoLancamento: gruposLancamentos) {
            totalGeral += grupoLancamento.getTotal();
        }
    }

    /*
    O total geral precisa ser zerado, o arquivo é escrito a cada final de chunk, por causa disso precisamos chamar esse evento
    Lembrando sempre que a classe precisa estar registrada como listener no step.
     */
    @AfterChunk
    public void zerarTotalGeralRodape(ChunkContext chunkContext) {
        totalGeral = 0.0;
    }

}
