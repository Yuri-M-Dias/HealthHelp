package br.ufg.inf.pes.healthhelp.service;

import java.util.Calendar;

import br.ufg.inf.pes.healthhelp.dao.AtendimentoDAO;
import br.ufg.inf.pes.healthhelp.model.Agenda;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link br.ufg.inf.pes.healthhelp.model.Atendimento}.
 */

public class AtendimentoService {
    private AtendimentoDAO atendimentoDAO;

    public AtendimentoService() {
        atendimentoDAO = new AtendimentoDAO();
    }

    public void buscarAtendimentos(Agenda[] agendas, Calendar diaOcorrencia) {
        atendimentoDAO.buscarAtendimentos(agendas, diaOcorrencia);
    }
}
