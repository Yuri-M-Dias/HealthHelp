package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

public class LocalAtendimentoService {
    private static final String TAG = "LocalAtendimentoService";
    private LocalAtendimentoDAO localAtendimentoDAO;

    public LocalAtendimentoService() {
        localAtendimentoDAO = new LocalAtendimentoDAO();
    }

    public void solicitarListaLocaisAtendimento() {
        localAtendimentoDAO.buscarTodos();
    }

    public void salvar(LocalAtendimento localAtendimento) {
        if (localAtendimento.getId() == null) {
            localAtendimentoDAO.inserir(localAtendimento);
        } else {
            localAtendimentoDAO.atualizar(localAtendimento);
        }
    }
}
