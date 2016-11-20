package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.Usuario;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a uma {@link Atuacao}.
 */
public class AtuacaoDAO extends AbstractDAO<Atuacao> {
    public AtuacaoDAO() {
        super(AtuacaoDAO.class.getCanonicalName(), "atuacao");
    }

    @Override
    public void buscarTodos() {
        //TODO
    }

    @Override
    public void buscarPelaId(String id) {
        //TODO
    }

    /**
     * Busca todas as atuações de um usuário.
     *
     * @param usuario usuário pelo qual as ocupações serão procuradas.
     */
    public void buscarPorUsuario(Usuario usuario) {
        //TODO
    }

    @Override
    public void inserir(Atuacao objeto) {

    }

    @Override
    public void remover(Atuacao objeto) {
        //TODO
    }

    @Override
    public void atualizar(Atuacao objeto) {
        //TODO
    }

}
