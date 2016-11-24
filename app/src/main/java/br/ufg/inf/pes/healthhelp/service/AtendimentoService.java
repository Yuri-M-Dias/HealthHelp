package br.ufg.inf.pes.healthhelp.service;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Paciente;
import br.ufg.inf.pes.healthhelp.model.event.AtendimentoEvent;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link br.ufg.inf.pes.healthhelp.model.Atendimento}.
 */

public class AtendimentoService {

    public void buscarAtendimentos(final Agenda agenda, final Calendar diaOcorrencia) {
        AsyncExecutor.create().execute(
            new AsyncExecutor.RunnableEx() {
                @Override
                public void run() throws Exception {
                    List<Atendimento> atendimentos = new ArrayList<>();
                    Atendimento atendimento = new Atendimento();
                    atendimento.setAgenda(agenda);
                    atendimento.setPaciente(new Paciente());
                    atendimento.setHoraInicio(Calendar.getInstance());
                    atendimento.getHoraInicioCalendar().set(Calendar.HOUR_OF_DAY, 9);
                    atendimento.getHoraInicioCalendar().set(Calendar.MINUTE, 0);

                    atendimento.setHoraFim(Calendar.getInstance());
                    atendimento.getHoraFimCalendar().set(Calendar.HOUR_OF_DAY, 9);
                    atendimento.getHoraFimCalendar().set(Calendar.MINUTE, 20);

                    if(atendimento.mesmaDataInicio(diaOcorrencia)) {
                        atendimentos.add(atendimento);
                    }

                    atendimento = new Atendimento();
                    atendimento.setAgenda(agenda);
                    atendimento.setPaciente(new Paciente());
                    atendimento.setHoraInicio(Calendar.getInstance());
                    atendimento.getHoraInicioCalendar().set(Calendar.HOUR_OF_DAY, 9);
                    atendimento.getHoraInicioCalendar().set(Calendar.MINUTE, 40);

                    atendimento.setHoraFim(Calendar.getInstance());
                    atendimento.getHoraFimCalendar().set(Calendar.HOUR_OF_DAY, 10);
                    atendimento.getHoraFimCalendar().set(Calendar.MINUTE, 00);

                    if(atendimento.mesmaDataInicio(diaOcorrencia)) {
                        atendimentos.add(atendimento);
                    }

                    Thread.sleep(2000);

                    EventBus.getDefault().post(new AtendimentoEvent<>(atendimentos, diaOcorrencia));
                }
            }
        );
    }
}
