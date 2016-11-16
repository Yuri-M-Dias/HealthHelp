package br.ufg.inf.pes.healthhelp.dao;

import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class AgendaDAOTest {

    private AgendaDAO agendaDAO;

    private Agenda agendaCallback;
    private Agenda agendaCriar;
    private Agenda agendaAtualizar;
    private Agenda agendaRemover;
    private Agenda agendaBuscarPorId;
    private Agenda agendaBuscarPorNome;
    private ArrayList<Agenda> agendasBusca;

    @Before
    public void setUp() throws Exception {


        agendaDAO = new AgendaDAO(
                FirebaseDatabase.getInstance().getReferenceFromUrl(
                        "ws://test.firebase.localhost:5000/") );



        agendaCallback = null;

        agendaCriar = preencheAgenda();
        agendaCriar.setNome("Agenda Criar 1");

        agendaAtualizar = preencheAgenda();
        agendaAtualizar.setNome("Agenda Atualizar 1");

        agendaRemover = preencheAgenda();
        agendaRemover.setNome("Agenda Remover 1");

        agendaBuscarPorId = preencheAgenda();
        agendaBuscarPorId.setNome("Agenda Buscar por ID 1");

        agendaBuscarPorNome = preencheAgenda();
        agendaBuscarPorNome.setNome("Agenda Buscar por Nome 1");


        agendaDAO.setDatabaseCallback(retornaCallbackTeste());

    }

   @Test
    public void inserirIsCorrect() throws Exception {


       agendaCriar.setId(agendaDAO.inserir(agendaCriar));

   }


  @Test
    public void atualizarIsCorrect() throws Exception {


      agendaAtualizar.setId(agendaDAO.inserir(agendaAtualizar));

      agendaDAO.atualizar(agendaAtualizar);

    }

    @Test
    public void removerIsCorrect() throws Exception {

        agendaRemover.setId(agendaDAO.inserir(agendaRemover));

        agendaDAO.remover( agendaRemover );





    }

    @Test
    public void testBuscarPelaIdIsCorrect() throws Exception {


        //insere antes para obter um id no banco e testar a busca
       agendaBuscarPorId.setId( agendaDAO.inserir(agendaBuscarPorId) );

        // pega da DAO de agenda a agenda recem inserida com o ID gerado pelo firebase, pois não se tem ele aqui
        agendaDAO.buscarPelaId(agendaBuscarPorId.getId());

    }
    @Test
    public void testBuscarTodosIsCorrect() throws Exception {

        agendasBusca = new ArrayList<>();

        for (int i =0; i <4; i ++ ){
            agendasBusca.add(preencheAgenda());
            agendasBusca.get(i).setNome("Agenda lista " + (i+1));
            agendaDAO.inserir(agendasBusca.get(i));
        }

        agendaDAO.buscarTodos();

    }

    @Test
    public void testBuscarPorNomeIsCorrect() throws Exception {

        agendaBuscarPorNome.setId(agendaDAO.inserir(agendaBuscarPorNome));

        agendaDAO.buscarPeloNome(agendaBuscarPorNome.getNome());

    }

    private DatabaseCallback retornaCallbackTeste() {
        return new DatabaseCallback() {
            @Override
            public void onComplete(Object object) {
                    // salva agenda salva ja com ID auto gerado
                    agendaCallback = (Agenda) object;

                    if( !(agendaCallback.getNome().equals(agendaCriar.getNome())   ||
                            agendaCallback.getNome().equals(agendaAtualizar.getNome() ) ||
                            agendaCallback.getNome().equals( agendaRemover.getNome() ) ||
                            agendaCallback.getNome().equals( agendaBuscarPorId.getNome() ) ||
                            agendaCallback.getNome().equals( agendaBuscarPorNome.getNome() ) ||
                            agendaCallback.getNome().contains("Agenda lista")
                    ) ) {
                        fail();
                    }
            }
            @Override
            public void onError(DatabaseError exception) {
                throw new DatabaseException(exception.getMessage());
            }
        };
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

        horariosAt.add(new PeriodoTempo("10", "17", "06-11-2016","12-11-2016", diasSemana ));

        ArrayList horariosBloq = new ArrayList<PeriodoTempo>();
        horariosBloq.add( new PeriodoTempo("08","18","12-11-2016", "12-11-2016", diasSemana));

        agendaPreenche.setHorariosAtendimento(horariosAt);
        agendaPreenche.setHorariosBloqueados(horariosBloq);
        agendaPreenche.setTempoPadrao(45);

        return agendaPreenche;
    }


}
