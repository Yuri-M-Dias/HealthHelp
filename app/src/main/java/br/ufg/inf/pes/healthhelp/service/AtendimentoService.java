package br.ufg.inf.pes.healthhelp.service;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.dao.AtendimentoDAO;
import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Paciente;
import br.ufg.inf.pes.healthhelp.model.event.PaginadorDiasEvent;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link br.ufg.inf.pes.healthhelp.model.Atendimento}.
 */

public class AtendimentoService {
    private AtendimentoDAO atendimentoDAO;

    public AtendimentoService() {
        atendimentoDAO = new AtendimentoDAO();
    }

    public void buscarAtendimentos(final Agenda agenda, final Calendar diaOcorrencia) {
        atendimentoDAO.buscarAtendimentos(agenda, diaOcorrencia);
    }
}
