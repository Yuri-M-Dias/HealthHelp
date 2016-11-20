package br.ufg.inf.pes.healthhelp.dao;

import android.support.test.espresso.base.MainThread;
import android.support.test.runner.AndroidJUnit4;

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

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class AgendaDAOTest {

    private AgendaDAO agendaDAO;

    private Agenda agendaCriar;
    /*private Agenda agendaAtualizar;
    private Agenda agendaRemover;
    private Agenda agendaBuscarPorId;
    private Agenda agendaBuscarPorNome;*/
    private ArrayList<Agenda> agendasBusca;

    public AgendaDAOTest() {
        EventBus.getDefault().register(this);
    }

    @Before
    public void setUp() throws Exception {


        agendaDAO = new AgendaDAO(
                FirebaseDatabase.getInstance().getReferenceFromUrl(
                        "ws://test.firebase.localhost:5000/") );




        agendaCriar = preencheAgenda();
        agendaCriar.setNome("Agenda Criar 1");


        agendasBusca = new ArrayList<>();

        for (int i =0; i <4; i ++ ){
            agendasBusca.add(preencheAgenda());
            agendasBusca.get(i).setNome("Agenda lista " + (i+1));
            agendaDAO.inserir(agendasBusca.get(i));
        }

        agendaDAO.buscarTodos();

        /*agendaAtualizar = preencheAgenda();
        agendaAtualizar.setNome("Agenda Atualizar 1");

        agendaRemover = preencheAgenda();
        agendaRemover.setNome("Agenda Remover 1");

        agendaBuscarPorId = preencheAgenda();
        agendaBuscarPorId.setNome("Agenda Buscar por ID 1");

        agendaBuscarPorNome = preencheAgenda();
        agendaBuscarPorNome.setNome("Agenda Buscar por Nome 1");*/

    }

    private Agenda preencheAgenda() {
        Agenda agendaPreenche = new Agenda();
        ArrayList horariosAt = new ArrayList<PeriodoTempo>();
        ArrayList diasSemana = new ArrayList<String>();
        diasSemana.add(DayOfWeek.SUNDAY.getValue());
        diasSemana.add(DayOfWeek.MONDAY.getValue());
        diasSemana.add(DayOfWeek.TUESDAY.getValue());
        diasSemana.add(DayOfWeek.WEDNESDAY.getValue());
        diasSemana.add(DayOfWeek.SATURDAY.getValue());

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
        agendaDAO.atualizar(agendaCriar);

        agendaDAO.remover(agendaCriar);
        // pega da DAO de agenda a agenda recem inserida com o ID gerado pelo firebase, pois não se tem ele aqui
        agendaDAO.buscarPelaId(agendaCriar.getId());

        agendaDAO.buscarPeloNome(agendaCriar.getNome());

        agendaDAO.buscarTodos();

    }
    @Subscribe
    public void onDatabaseEventAgenda(DatabaseEvent<Agenda> databaseEvent){
        assertTrue(databaseEvent.getObjeto().getNome().equals(agendaCriar.getNome()));
    }

    @Subscribe
    public void onDatabaseEvent(DatabaseEvent<String> databaseEvent) {
        assertTrue(
            databaseEvent.getObjeto().equals("Agenda adicionada " +agendaCriar.getNome()) ||
                databaseEvent.getObjeto().contains("Agenda adicionada Agenda lista ") ||
                databaseEvent.getObjeto().equals("Agenda removida " +agendaCriar.getNome()) ||
                databaseEvent.getObjeto().equals("Agenda atualizada " +agendaCriar.getNome()));

    }

    @Subscribe
    public void onDatabaseListEvent(DatabaseEvent<ArrayList<Agenda>> databaseEvent) {
        assertTrue(databaseEvent.getObjeto().containsAll(agendasBusca));
    }

    @Subscribe
    public void onDatabaseErrorEvent(DatabaseEvent<DatabaseError> databaseError) {
        throw databaseError.getObjeto().toException();
    }

    @After
    public void tearDown() throws Exception {
        EventBus.getDefault().unregister(this);

    }
}
