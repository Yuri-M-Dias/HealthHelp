package br.ufg.inf.pes.healthhelp.dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

public class LocalAtendimentoDAOStub extends LocalAtendimentoDAO {
    @Override
    public String inserir(final LocalAtendimento localAtendimento) {
        AsyncExecutor.create().execute(
            new AsyncExecutor.RunnableEx() {
                @Override
                public void run() throws Exception {
                    EventBus.getDefault().post(new DatabaseEvent<>("Local de Atendimento salvo com sucesso"));
                }
            }
        );
        return null;
    }

    @Override
    public void atualizar(final LocalAtendimento localAtendimento) {
        AsyncExecutor.create().execute(
            new AsyncExecutor.RunnableEx() {
                @Override
                public void run() throws Exception {
                    EventBus.getDefault().post(new DatabaseEvent<>("Local de Atendimento salvo com sucesso"));
                }
            }
        );
    }

}
