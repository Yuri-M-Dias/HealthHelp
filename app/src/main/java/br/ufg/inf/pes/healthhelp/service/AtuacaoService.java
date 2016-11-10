package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.AtuacaoDAO;
import br.ufg.inf.pes.healthhelp.model.Atuacao;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link Atuacao}.
 */

public class AtuacaoService {
    AtuacaoDAO atuacaoDAO;

    public AtuacaoService() {
        this.atuacaoDAO = new AtuacaoDAO();
    }

    void solicitarListaAtuacoes() {
        atuacaoDAO.buscarTodos();
    }

    void salvar(Atuacao atuacao) {
        if (atuacao.getId() == null) {
            atuacaoDAO.inserir(atuacao);
        } else {
            atuacaoDAO.atualizar(atuacao);
        }
    }
}
