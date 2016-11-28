package br.ufg.inf.pes.healthhelp.dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Paciente;
import br.ufg.inf.pes.healthhelp.model.event.PaginadorDiasEvent;

public class AtendimentoDAO extends AbstractDAO {

    public AtendimentoDAO() {
        super(AtendimentoDAO.class.getCanonicalName(), "atendimento", Atendimento.class);
    }

    public void buscarAtendimentos(final List<Agenda> agendas, final Calendar diaOcorrencia) {
        //TODO: Substituir a implementação stub abaixo pela implementação correta
        AsyncExecutor.create().execute(
            new AsyncExecutor.RunnableEx() {
                @Override
                public void run() throws Exception {
                    List<Atendimento> atendimentos = new ArrayList<>();
                    Atendimento atendimento = new Atendimento();
                    atendimento.setAgenda(agendas.get(0));
                    atendimento.setPaciente(new Paciente());
                    atendimento.getPaciente().setNome("Patrícia Silva");
                    atendimento.setHoraInicio(Calendar.getInstance());
                    atendimento.getHoraInicioCalendar().set(Calendar.HOUR_OF_DAY, 9);
                    atendimento.getHoraInicioCalendar().set(Calendar.MINUTE, 0);

                    atendimento.setHoraFim(Calendar.getInstance());
                    atendimento.getHoraFimCalendar().set(Calendar.HOUR_OF_DAY, 9);
                    atendimento.getHoraFimCalendar().set(Calendar.MINUTE, 20);

                    if (atendimento.mesmaDataInicio(diaOcorrencia)) {
                        atendimentos.add(atendimento);
                    }

                    atendimento = new Atendimento();
                    atendimento.setAgenda(agendas.get(1));
                    atendimento.setPaciente(new Paciente());
                    atendimento.getPaciente().setNome("João Roberto");
                    atendimento.setHoraInicio(Calendar.getInstance());
                    atendimento.getHoraInicioCalendar().set(Calendar.HOUR_OF_DAY, 15);
                    atendimento.getHoraInicioCalendar().set(Calendar.MINUTE, 00);

                    atendimento.setHoraFim(Calendar.getInstance());
                    atendimento.getHoraFimCalendar().set(Calendar.HOUR_OF_DAY, 16);
                    atendimento.getHoraFimCalendar().set(Calendar.MINUTE, 00);

                    if (atendimento.mesmaDataInicio(diaOcorrencia)) {
                        atendimentos.add(atendimento);
                    }

                    Thread.sleep(500);

                    EventBus.getDefault().post(new PaginadorDiasEvent<>(atendimentos, diaOcorrencia));
                }
            }
        );
    }
}
