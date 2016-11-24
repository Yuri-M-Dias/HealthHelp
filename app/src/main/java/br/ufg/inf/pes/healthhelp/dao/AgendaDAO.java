package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.Agenda;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a uma {@link Agenda}.
 */
public class AgendaDAO extends AbstractDAO<Agenda> {

    public AgendaDAO() {
        super(AgendaDAO.class.getCanonicalName(), "agenda", Agenda.class);
    }

}
