package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.AgendaDAO;
import br.ufg.inf.pes.healthhelp.model.Agenda;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link Agenda}.
 */

public class AgendaService {
    AgendaDAO agendaDAO;

    public AgendaService() {
        this.agendaDAO = new AgendaDAO();
    }

    void salvar(Agenda agenda) {
        if (agenda.getId() == null) {
            agendaDAO.inserir(agenda);
        } else {
            agendaDAO.atualizar(agenda);
        }
    }

}
