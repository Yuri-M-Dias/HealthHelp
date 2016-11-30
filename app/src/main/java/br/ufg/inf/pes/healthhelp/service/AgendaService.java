package br.ufg.inf.pes.healthhelp.service;

import java.util.List;

import br.ufg.inf.pes.healthhelp.dao.AgendaDAO;
import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link Agenda}.
 */

public class AgendaService {
    private AgendaDAO agendaDAO;

    public AgendaService() {
        this.agendaDAO = new AgendaDAO();
    }

    public void solicitarAgenda(String id) {
        agendaDAO.buscarPelaId(id);
    }

    public void solicitarListaAgendas(List<LocalAtendimento> locais) {

    }

    void salvar(Agenda agenda) {
        if (agenda.getId() == null) {
            agendaDAO.inserir(agenda);
        } else {
            agendaDAO.atualizar(agenda);
        }
    }

}
