package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.AtuacaoDAO;
import br.ufg.inf.pes.healthhelp.model.Atuacao;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link Atuacao}.
 * Created by cleber on 02/11/16.
 */

public class AtuacaoService {
    AtuacaoDAO atuacaoDAO;

    public AtuacaoService() {
        this.atuacaoDAO = new AtuacaoDAO();
    }

    void solicitarLitaAtuacoes(){
        atuacaoDAO.buscarTodos();
    }
    void salvar(Atuacao agenda) {
        if(agenda.getId() == null) {
            atuacaoDAO.inserir(agenda);
        } else {
            atuacaoDAO.atualizar(agenda);
        }
    }
}
