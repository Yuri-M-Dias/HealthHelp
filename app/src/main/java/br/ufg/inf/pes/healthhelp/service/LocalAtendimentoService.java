package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.DatabaseCallback;
import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;

/**
 * Created by deassisrosal on 10/6/16.
 */

public class LocalAtendimentoService {
    private LocalAtendimentoDAO localAtendimentoDAO;

    private static final String TAG = "LocalAtendimentoService";

    public void solicitarListaLocaisAtendimento(DatabaseCallback callback) {
        localAtendimentoDAO = new LocalAtendimentoDAO(callback);
        localAtendimentoDAO.carregarTodos();

    }

}
