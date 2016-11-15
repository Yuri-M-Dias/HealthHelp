package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.DatabaseCallback;
import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;
import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAOStub;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

public class LocalAtendimentoService {
    private static final String TAG = "LocalAtendimentoService";
    private LocalAtendimentoDAO localAtendimentoDAO;

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

        if (localAtendimento.getId() == null) {
            localAtendimentoDAO.inserir(localAtendimento);
        } else {
            localAtendimentoDAO.atualizar(localAtendimento);
        }
    }
}
