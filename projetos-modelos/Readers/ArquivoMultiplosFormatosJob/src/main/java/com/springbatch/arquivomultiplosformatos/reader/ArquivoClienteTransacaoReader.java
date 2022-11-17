package com.springbatch.arquivomultiplosformatos.reader;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;
import org.springframework.batch.item.*;

public class ArquivoClienteTransacaoReader implements ItemStreamReader<Cliente> {

    private Object objetoAtual;
    private ItemStreamReader<Object> delegate;

    // Esse objeto delegate vem do Step
    public ArquivoClienteTransacaoReader(ItemStreamReader<Object> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Cliente read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (objetoAtual == null)
            objetoAtual = delegate.read(); // Leio o que está no contexto da aplicação

        // O leitor só vai ler clientes, uma vez lido eu sei que é um cliente
        Cliente cliente = (Cliente) objetoAtual;
        objetoAtual = null;

        // Se ainda não acabei o arquivo e o Cliente é diferente de nulo
        // peek é espiar o objetoAtual para ver se é uma transacao
        // É transação? Adiciona para o cliente, não é retorna o cliente e zera o objetoAtual
        if (cliente != null) {
            while (peek() instanceof Transacao)
                cliente.getTransacoes().add((Transacao) objetoAtual);
        }
        return cliente;

    }

    private Object peek() throws Exception {
        objetoAtual = delegate.read();
        return objetoAtual;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }
}
