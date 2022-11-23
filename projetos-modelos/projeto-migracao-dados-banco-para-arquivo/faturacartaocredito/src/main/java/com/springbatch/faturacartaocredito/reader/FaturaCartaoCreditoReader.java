package com.springbatch.faturacartaocredito.reader;

import com.springbatch.faturacartaocredito.dominio.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.dominio.Transacao;
import org.springframework.batch.item.*;

public class FaturaCartaoCreditoReader implements ItemStreamReader<FaturaCartaoCredito> {

    private ItemStreamReader<Transacao> transacaoDelegate;
    private Transacao transacaoAtual;

    @Override
    public FaturaCartaoCredito read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (transacaoAtual == null)
            transacaoAtual = transacaoDelegate.read();

        FaturaCartaoCredito faturaCartaoCredito = null;
        Transacao transacao = transacaoAtual;

        transacaoAtual = null;

        if (transacao != null) {
            faturaCartaoCredito = new FaturaCartaoCredito();
            faturaCartaoCredito.setCartaoCredito(transacao.getCartaoCredito());
            faturaCartaoCredito.setCliente(transacao.getCartaoCredito().getCliente());
            faturaCartaoCredito.getTransacoes().add(transacao);
        }

        while (ehTransacaoRelacionada(transacao))
            faturaCartaoCredito.getTransacoes().add(transacaoAtual);

        return faturaCartaoCredito;

    }

    private boolean ehTransacaoRelacionada(Transacao transacao) throws Exception {
        return peek() != null && transacao.getCartaoCredito().getNumero() == transacaoAtual.getCartaoCredito().getNumero();
    }

    private Transacao peek() throws Exception {
        transacaoAtual = transacaoDelegate.read();
        return transacaoAtual;
    }

    public FaturaCartaoCreditoReader(ItemStreamReader<Transacao> transacaoDelegate) {
        this.transacaoDelegate = transacaoDelegate;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        transacaoDelegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        transacaoDelegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        transacaoDelegate.close();
    }
}
