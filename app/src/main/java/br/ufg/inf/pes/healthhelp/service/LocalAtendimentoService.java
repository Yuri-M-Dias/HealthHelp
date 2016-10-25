package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.DatabaseCallback;
import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;

/**
 * Created by deassisrosal on 10/6/16.
 */

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

}
