package br.ufg.inf.pes.healthhelp.dao;

import android.os.SystemClock;
import android.support.test.espresso.base.MainThread;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class
AgendaDAOTest {

    private AgendaDAO agendaDAO;

    private Agenda agendaCriar;
    private Object eventoDao;
    private ArrayList<Agenda> agendasBusca;

    public AgendaDAOTest() {
        EventBus.getDefault().register(this);
    }

    @Before
    public void setUp() throws Exception {


        agendaDAO = new AgendaDAO(
                FirebaseDatabase.getInstance().getReferenceFromUrl(
                        "https://health-help-8f985.firebaseio.com/") );

        agendaCriar = preencheAgenda();
        agendaCriar.setNome("Agenda Criar 1");

        agendasBusca = new ArrayList<>();
        eventoDao = null;

    }

    private Agenda preencheAgenda() {
        Agenda agendaPreenche = new Agenda();
        ArrayList horariosAt = new ArrayList<PeriodoTempo>();
        ArrayList diasSemana = new ArrayList<String>();
        diasSemana.add(String.valueOf(DayOfWeek.SUNDAY.getValue()));
        diasSemana.add(String.valueOf(DayOfWeek.MONDAY.getValue()));
        diasSemana.add(String.valueOf(DayOfWeek.TUESDAY.getValue()));
        diasSemana.add(String.valueOf(DayOfWeek.WEDNESDAY.getValue()));
        diasSemana.add(String.valueOf(DayOfWeek.SATURDAY.getValue()));

        horariosAt.add( new PeriodoTempo("10:00", "17:00", "06/11/2016","12/11/2016", diasSemana ));

        ArrayList horariosBloq = new ArrayList<PeriodoTempo>();
        horariosBloq.add( new PeriodoTempo("08:00", "18:00", "12/11/2016", "12/11/2016", diasSemana) );

        agendaPreenche.setHorariosAtendimento(horariosAt);
        agendaPreenche.setHorariosBloqueados(horariosBloq);
        agendaPreenche.setTempoPadraoMinutos(45);

        return agendaPreenche;
    }

    @Test
    public void daoIsCorrect() {
        agendaDAO.inserir(agendaCriar);
        esperarEvento();
        assertEquals("Agenda"+ agendaCriar.getId(), eventoDao);
        eventoDao = null;

        agendaDAO.buscarPelaId(agendaCriar.getId());
        esperarEvento();
        assertEquals(agendaCriar.getId(), ((Agenda)eventoDao).getId());
        eventoDao = null;

        agendaDAO.buscarPeloNome(agendaCriar.getNome());
        esperarEvento();
        assertEquals(agendaCriar.getId(), ((Agenda)eventoDao).getId());
        eventoDao = null;

        Agenda agendaAtualizar = agendaCriar;
        agendaAtualizar.setNome("Agenda Atualizar 1");
        agendaDAO.atualizar(agendaAtualizar);
        esperarEvento();
        assertEquals("Agenda"+ agendaAtualizar.getId(), eventoDao);
        eventoDao = null;

        agendaDAO.remover(agendaAtualizar);
        esperarEvento();
        assertEquals("Agenda" + agendaAtualizar.getId(), eventoDao);
        eventoDao = null;



        for (int i =0; i <4; i ++ ){
            agendasBusca.add(preencheAgenda());
            agendasBusca.get(i).setNome("Agenda lista " + (i+1));
            agendaDAO.inserir(agendasBusca.get(i));
            esperarEvento();
            eventoDao = null;
        }

        agendaDAO.buscarTodos();
        esperarEvento();

        assertTrue(buscouComSucesso());


    }

    private boolean buscouComSucesso() {
        boolean contemTodos = true;

        for (Agenda agendaInserida:agendasBusca) {
            // checa se cada elemento inserido foi buscado no firebase database
            for (Agenda agenda :
                (ArrayList<Agenda>) eventoDao) {
                contemTodos = false;
                if(agendaInserida.getId().equals(agenda.getId()))
                    contemTodos = true;
            }

        }

        return contemTodos;
    }

    private void esperarEvento() {
        while (eventoDao == null){
            Log.w("esperarEvento", "esperando eventBus" );
            SystemClock.sleep(500);

        }
    }

    @Subscribe
    public void onDatabaseEvent(DatabaseEvent<String> databaseEvent) {
        eventoDao = databaseEvent.getObjeto();
    }


    @Subscribe
    public void onDatabaseEventBuscaAgenda(Agenda databaseEvent) {
        eventoDao = databaseEvent;
    }

    @Subscribe
    public void onDatabaseEventBuscaLista(ArrayList<Agenda> databaseEvent) {
        eventoDao = databaseEvent;
    }

    @After
    public void tearDown() throws Exception {
        EventBus.getDefault().unregister(this);

    }
}
