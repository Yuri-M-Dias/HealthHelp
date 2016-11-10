package br.ufg.inf.pes.healthhelp.service;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import br.ufg.inf.pes.healthhelp.dao.DatabaseCallback;
import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;
import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAOStub;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

public class LocalAtendimentoService {
    private LocalAtendimentoDAO localAtendimentoDAO;

    private static final String TAG = "LocalAtendimentoService";

    public LocalAtendimentoService() {
        localAtendimentoDAO = new LocalAtendimentoDAO();
    }

    public void solicitarListaLocaisAtendimento(DatabaseCallback callback) {
        localAtendimentoDAO.setDatabaseCallback(callback);
        localAtendimentoDAO.buscarTodos();
    }

    public void solicitarBuscaLocalAtendimento(DatabaseCallback callback, String nomeLocal) {
        localAtendimentoDAO.setDatabaseCallback(callback);
        localAtendimentoDAO.buscarPorNome(nomeLocal);
    }

    public void salvar(LocalAtendimento localAtendimento) {
        localAtendimentoDAO = new LocalAtendimentoDAOStub();

        if(localAtendimento.getId() == null) {
            localAtendimentoDAO.inserir(localAtendimento);
        } else {
            localAtendimentoDAO.atualizar(localAtendimento);
        }
    }
}
