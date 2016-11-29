package br.ufg.inf.pes.healthhelp.dao;

import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AgendaDAOTest {

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
            FirebaseDatabase
                .getInstance()
                .getReferenceFromUrl("https://health-help-8f985.firebaseio.com/")
        );
        agendaCriar = preencheAgenda();
        agendaCriar.setNome("Agenda Criar 1");
        agendasBusca = new ArrayList<>();
        eventoDao = null;
    }

    private Agenda preencheAgenda() {
        Agenda agendaPreenche = new Agenda();
        ArrayList<PeriodoTempo> horariosAt = new ArrayList<>();
        ArrayList<DayOfWeek> diasSemana = new ArrayList<>();
        diasSemana.add(DayOfWeek.SUNDAY);
        diasSemana.add(DayOfWeek.MONDAY);
        diasSemana.add(DayOfWeek.TUESDAY);
        diasSemana.add(DayOfWeek.WEDNESDAY);
        diasSemana.add(DayOfWeek.SATURDAY);

        horariosAt.add(new PeriodoTempo("10:00", "17:00", "06/11/2016", "12/11/2016", diasSemana));

        ArrayList<PeriodoTempo> horariosBloq = new ArrayList<PeriodoTempo>();
        horariosBloq.add(new PeriodoTempo("08:00", "18:00", "12/11/2016", "12/11/2016", diasSemana));

        agendaPreenche.setHorariosAtendimento(horariosAt);
        agendaPreenche.setHorariosBloqueados(horariosBloq);
        agendaPreenche.setTempoPadraoMinutos(45);

        return agendaPreenche;
    }

    @Test
    public void daoIsCorrect() {
        agendaDAO.inserir(agendaCriar);
        esperarEvento();
        Agenda eventoCriado = (Agenda) eventoDao;
        assertEquals(agendaCriar.getId(), eventoCriado.getId());
        eventoDao = null;

        agendaDAO.buscarPelaId(agendaCriar.getId());
        esperarEvento();
        eventoCriado = (Agenda) eventoDao;
        assertEquals(agendaCriar.getId(), eventoCriado.getId());
        eventoDao = null;

        Agenda agendaAtualizar = agendaCriar;
        agendaAtualizar.setNome("Agenda Atualizar 1");
        agendaDAO.atualizar(agendaAtualizar);
        esperarEvento();
        String idEventoCriado = (String) eventoDao;
        assertEquals(agendaAtualizar.getId(), idEventoCriado);
        eventoDao = null;

        agendaDAO.remover(agendaAtualizar);
        esperarEvento();
        idEventoCriado = (String) eventoDao;
        assertEquals(agendaAtualizar.getId(), idEventoCriado);
        eventoDao = null;


        for (int i = 0; i < 4; i++) {
            agendasBusca.add(preencheAgenda());
            agendasBusca.get(i).setNome(UUID.randomUUID().toString());
            agendaDAO.inserir(agendasBusca.get(i));
            esperarEvento();
            eventoDao = null;
        }

        agendaDAO.buscarTodos();
        esperarEvento();

        List<Agenda> listaAgendasEncontradas = (ArrayList<Agenda>) eventoDao;
        boolean buscouTodosComSucesso = buscouTodosComSucesso(listaAgendasEncontradas);
        assertTrue(buscouTodosComSucesso);
        for (Agenda agendaLocal : agendasBusca) {
            agendaDAO.remover(agendaLocal);
        }
    }

    private boolean buscouTodosComSucesso(List<Agenda> resultadoBuscado) {
        //Não pode garantir ordem com ArrayList. Esse teste não faz sentido.
        boolean contemTodos = false;

        for (Agenda agendaInserida : agendasBusca) {
            for (Agenda agenda : resultadoBuscado) {
                contemTodos = false;
                if (agendaInserida.getId().equals(agenda.getId())) {
                    contemTodos = true;
                }
            }
        }

        return contemTodos;
    }

    private void esperarEvento() {
        while (eventoDao == null) {
            Log.w("esperarEvento", "esperando eventBus");
            SystemClock.sleep(500);
        }
    }

    @Subscribe
    public void onDatabaseEvent(String databaseEvent) {
        eventoDao = databaseEvent;
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
