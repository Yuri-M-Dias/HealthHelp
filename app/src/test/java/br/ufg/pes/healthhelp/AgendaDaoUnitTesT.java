package br.ufg.pes.healthhelp;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import br.ufg.inf.pes.healthhelp.dao.AgendaDAO;
import br.ufg.inf.pes.healthhelp.dao.DatabaseCallback;
import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;

import static org.junit.Assert.assertEquals;


public class AgendaDaoUnitTest{

    private Context mContext = null;
    private AgendaDAO agendaDAO;
    Agenda agenda;


    @Before
    public void setUp() throws Exception {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setDatabaseUrl("ws://127.0.1:5000").build();
        FirebaseApp.initializeApp(mContext, options);

        agendaDAO = new AgendaDAO();

        agendaDAO.setDatabaseCallback(new DatabaseCallback() {
            @Override
            public void onComplete(Object object) {
                // checa se o objeto inserido é o mesmo capturado pelo onComplete do listener, isto é, a inserção deu certo
                assertEquals(agenda, (Agenda) object);
            }

            @Override
            public void onError(DatabaseError exception) {
                throw new DatabaseException(exception.getMessage());
            }
        });

    }

    @Test
    public void inserirIsCorrect() throws Exception {
        agenda = preencheAgenda("Agenda Criada 1");
        agendaDAO.inserir( agenda);

    }

    @Test
    public void atualizarIsCorrect() throws Exception {
        agenda = preencheAgenda("Agenda Atualizada 1");
        agendaDAO.atualizar(agenda);

    }

    @Test
    public void removerIsCorrect() throws Exception {
        agenda = preencheAgenda("Agenda Remover 1");
        agendaDAO.inserir(agenda);
        agendaDAO.remover(agenda);

    }

    @Test
    public void buscarPelaIdIsCorrect() throws Exception {
        agenda = preencheAgenda("Agenda Buscar por ID 1");
        agendaDAO.inserir(agenda);

        //insere antes para obter um id no banco e testar a busca
        agendaDAO.buscarPelaId(agenda.getId());

    }

    @Test
    public void buscarTodosIsCorrect() throws Exception {
        final ArrayList<Agenda> todasAgendas = new ArrayList<>();
        todasAgendas.add( preencheAgenda("Agenda lista 1") );
        todasAgendas.add( preencheAgenda("Agenda lista 2") );
        todasAgendas.add( preencheAgenda("Agenda lista 3") );
        todasAgendas.add( preencheAgenda("Agenda lista 4") );
        agendaDAO.inserir(preencheAgenda("Agenda lista 1"));
        agendaDAO.inserir(preencheAgenda("Agenda lista 2"));
        agendaDAO.inserir(preencheAgenda("Agenda lista 3"));
        agendaDAO.inserir(preencheAgenda("Agenda lista 4"));

        agendaDAO.setDatabaseCallback(new DatabaseCallback<ArrayList<Agenda>>(){

            @Override
            public void onComplete(ArrayList<Agenda> object) {
                assertEquals(todasAgendas, object);

            }

            @Override
            public void onError(DatabaseError exception) {
                throw new DatabaseException(exception.getMessage());
            }
        });

        agendaDAO.buscarTodos();

    }

    @Test
    public void buscarPorNomeIsCorrect() throws Exception {
        Agenda agendaBuscar = preencheAgenda("Agenda Buscar por Nome 1");
        agendaDAO.inserir(agendaBuscar);
        agendaDAO.buscarPeloNome("Agenda Buscar 1");
    }

    private Agenda preencheAgenda(String nome) {
        Agenda agendaPreenche = new Agenda();
        agendaPreenche.setNome(nome);
        ArrayList horariosAt = new ArrayList<PeriodoTempo>();
        ArrayList diasSemana = new ArrayList<DayOfWeek>();
        diasSemana.add(DayOfWeek.SUNDAY);
        diasSemana.add(DayOfWeek.MONDAY);
        diasSemana.add(DayOfWeek.TUESDAY);
        diasSemana.add(DayOfWeek.WEDNESDAY);
        horariosAt.add(new PeriodoTempo("10", "17", "06-11-2016","12-11-2016", diasSemana ));

        ArrayList horariosBloq = new ArrayList<PeriodoTempo>();
        diasSemana.add(DayOfWeek.SATURDAY);
        horariosBloq.add( new PeriodoTempo("08","18","12-11-2016", "12-11-2016", diasSemana));

        agendaPreenche.setHorariosAtendimento(horariosAt);
        agendaPreenche.setHorariosBloqueados(horariosBloq);

        return agendaPreenche;
    }
}
