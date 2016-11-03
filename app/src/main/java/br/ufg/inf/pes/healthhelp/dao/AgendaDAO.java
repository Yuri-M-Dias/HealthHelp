package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.Agenda;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a uma {@link Agenda}.
 * Created by cleber on 02/11/16.
 */
public class AgendaDAO extends AbstractDAO<Agenda>{
    public AgendaDAO() {
        super(AgendaDAO.class.getCanonicalName(), "agenda");
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
    public void inserir(Agenda objeto) {
        //TODO
    }

    @Override
    public void remover(Agenda objeto) {
        //TODO
    }

    @Override
    public void atualizar(Agenda objeto) {
        //TODO
    }
}
