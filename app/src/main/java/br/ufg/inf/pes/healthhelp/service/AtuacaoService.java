package br.ufg.inf.pes.healthhelp.service;

import java.util.List;

import br.ufg.inf.pes.healthhelp.dao.AtuacaoDAO;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
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
    public void solicitarListaAtuacoes(String id) {
        atuacaoDAO.buscarPelaId(id);
    }

    public void solicitarListaAtuacoesPorUsuario(Usuario usuario) {
        atuacaoDAO.buscarPorUsuario(usuario);
    }

    public void solicitarAtuacoesPorId(String id){
        atuacaoDAO.buscarPelaId(id);
    }

    public void solicitarListaProfissionaisSaude(List<LocalAtendimento> locaisAtendimento) {
        atuacaoDAO.buscarPorLocais(locaisAtendimento);
    }

    public void salvar(Atuacao atuacao) {
        if (atuacao.getId() == null) {
            atuacaoDAO.inserir(atuacao);
        } else {
            atuacaoDAO.atualizar(atuacao);
        }
    }

}
