package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.Atuacao;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a uma {@link Atuacao}.
 * Created by cleber on 02/11/16.
 */
public class AtuacaoDAO extends AbstractDAO<Atuacao>{
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

    @Override
    public void inserir(Atuacao objeto) {
        //TODO
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
