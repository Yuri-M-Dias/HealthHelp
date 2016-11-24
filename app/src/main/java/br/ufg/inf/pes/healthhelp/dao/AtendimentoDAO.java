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
        super(AtendimentoDAO.class.getCanonicalName(), "atendimento");
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
    public void inserir(Object objeto) {
        //TODO
    }

    @Override
    public void remover(Object objeto) {
        //TODO
    }

    @Override
    public void atualizar(Object objeto) {
        //TODO
    }

    public void buscarAtendimentos(final Agenda[] agendas, final Calendar diaOcorrencia) {
        //TODO: Substituir a implementação stub abaixo pela implementação correta
        AsyncExecutor.create().execute(
            new AsyncExecutor.RunnableEx() {
                @Override
                public void run() throws Exception {
                    List<Atendimento> atendimentos = new ArrayList<>();
                    Atendimento atendimento = new Atendimento();
                    atendimento.setAgenda(agendas[0]);
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
                    atendimento.setAgenda(agendas[0]);
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

                    Thread.sleep(3000);

                    EventBus.getDefault().post(new PaginadorDiasEvent<>(atendimentos, diaOcorrencia));
                }
            }
        );
    }
}
