package br.ufg.inf.pes.healthhelp.dao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a uma {@link Agenda}.
 */
public class AgendaDAO extends AbstractDAO<Agenda> {

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
        AsyncExecutor.create().execute(
            new AsyncExecutor.RunnableEx() {

                @Override
                public void run() throws Exception {

                    List<Agenda> agendas = new LinkedList<Agenda>();
                    Agenda agenda = new Agenda();
                    agenda.setNome("Cirurgias");
                    agenda.setId("22");
                    agendas.add(agenda);

                    Thread.sleep(2000);

                    EventBus.getDefault().post(new DatabaseEvent<>(agendas));
                }
            }
        );
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
