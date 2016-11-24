package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.AtuacaoDAO;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.Usuario;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link Atuacao}.
 */

public class AtuacaoService {
    private AtuacaoDAO atuacaoDAO;

    public AtuacaoService() {
        this.atuacaoDAO = new AtuacaoDAO();
    }

    /**
     * Solicita a lista de atuações de um usuário.
     *
     * @param usuario usuário pelo qual as ocupações serão procuradas.
     */
    void solicitarListaAtuacoes(Usuario usuario) {
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
