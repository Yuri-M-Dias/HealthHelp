package br.ufg.inf.pes.healthhelp.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.inf.pes.healthhelp.service.AtendimentoService;
import br.ufg.inf.pes.healthhelp.view.adapters.PaginadorDiasAdapter;
import br.ufg.pes.healthhelp.R;

public class AgendaActivity extends AppCompatActivity {

    public static final String ARG_ATUACAO = "atuacao";
    public static final String ARG_ATENDIMENTO_AGENDADO = "atendimento-agendado";
    public static final String ARG_PERMITE_PASSADO = "permite-ver-passado";
    public static final String ARG_TIPO_AGENDA = "tipo-agenda";

    public static final int SELECIONAR_HORARIO_REQUEST = 100;

    private PaginadorDiasAdapter paginadorDiasAdapter;
    private ViewPager paginadorDiasView;

    private Atuacao atuacao;
    Class<AgendaFragment> tipoAgenda;

    private Atendimento atendimento;
    private AtendimentoService atendimentoService;
    private boolean permiteVerPassado;

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public AtendimentoService getAtendimentoService() {
        return atendimentoService;
    }

    public Atuacao getAtuacao() {
        return atuacao;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar contexto = Calendar.getInstance();

        permiteVerPassado = getIntent().getBooleanExtra(ARG_PERMITE_PASSADO, false);
        atuacao = (Atuacao) getIntent().getSerializableExtra(ARG_ATUACAO);
        tipoAgenda = (Class<AgendaFragment>) getIntent().getSerializableExtra(ARG_TIPO_AGENDA);
        atuacao = (Atuacao) getIntent().getSerializableExtra(ARG_ATUACAO);

        if(atuacao == null){
            atuacao = criarAtuacao();
        }


        atendimentoService = new AtendimentoService();
        setContentView(R.layout.activity_agenda_disponivel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        paginadorDiasAdapter = new PaginadorDiasAdapter(getSupportFragmentManager(), permiteVerPassado, contexto, atuacao, tipoAgenda);

        paginadorDiasView = (ViewPager) findViewById(R.id.container);

        paginadorDiasView.setAdapter(paginadorDiasAdapter);
        paginadorDiasView.setCurrentItem(paginadorDiasAdapter.getItemPosition(contexto));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(paginadorDiasView);
    }

    private Atuacao criarAtuacao() {
        Atuacao atuacao = new Atuacao();
        atuacao.setAgendas(new ArrayList<Agenda>());
        Agenda agenda = new Agenda();
        agenda.setNome("Atendimento Geral");
        agenda.setId("id_agenda_atendimento_geral");
        agenda.setHorariosBloqueados(new ArrayList<PeriodoTempo>());
        agenda.setTempoPadraoMinutos(20);

        List<PeriodoTempo> horariosAtendimento = new ArrayList<>();

        PeriodoTempo periodo = new PeriodoTempo();
        periodo.setDataInicio("25/11/2016");
        periodo.setDataFim("25/12/2016");
        periodo.setHoraInicio("08:00");
        periodo.setHoraFim("12:00");
        List<DayOfWeek> diasSemana = new ArrayList<>();
        diasSemana.add(DayOfWeek.MONDAY);
        diasSemana.add(DayOfWeek.TUESDAY);
        diasSemana.add(DayOfWeek.WEDNESDAY);
        diasSemana.add(DayOfWeek.THURSDAY);
        diasSemana.add(DayOfWeek.FRIDAY);
        periodo.setDiasSemana(diasSemana);
        horariosAtendimento.add(periodo);

        periodo = new PeriodoTempo();
        periodo.setDataInicio("25/11/2016");
        periodo.setDataFim("25/12/2016");
        periodo.setHoraInicio("10:00");
        periodo.setHoraFim("16:00");
        diasSemana = new ArrayList<>();
        diasSemana.add(DayOfWeek.SATURDAY);
        periodo.setDiasSemana(diasSemana);
        horariosAtendimento.add(periodo);

        agenda.setHorariosAtendimento(horariosAtendimento);

        atuacao.getAgendas().add(agenda);

        agenda = new Agenda();
        agenda.setNome("Cirurgias");
        agenda.setId("id_agenda_cirurgias");
        agenda.setHorariosBloqueados(new ArrayList<PeriodoTempo>());
        agenda.setTempoPadraoMinutos(60);

        horariosAtendimento = new ArrayList<>();

        periodo = new PeriodoTempo();
        periodo.setDataInicio("25/11/2016");
        periodo.setDataFim("25/12/2016");
        periodo.setHoraInicio("14:00");
        periodo.setHoraFim("20:00");
        diasSemana = new ArrayList<>();
        diasSemana.add(DayOfWeek.MONDAY);
        diasSemana.add(DayOfWeek.TUESDAY);
        diasSemana.add(DayOfWeek.WEDNESDAY);
        diasSemana.add(DayOfWeek.THURSDAY);
        diasSemana.add(DayOfWeek.FRIDAY);
        periodo.setDiasSemana(diasSemana);
        horariosAtendimento.add(periodo);

        atuacao.getAgendas().add(agenda);

        PeriodoTempo periodoAlmoco = new PeriodoTempo();
        periodoAlmoco.setHoraInicio(Calendar.getInstance());
        periodoAlmoco.getHoraInicioCalendar().set(Calendar.HOUR_OF_DAY, 12);
        periodoAlmoco.getHoraInicioCalendar().set(Calendar.MINUTE, 20);
        periodoAlmoco.setHoraFim(Calendar.getInstance());
        periodoAlmoco.getHoraFimCalendar().set(Calendar.HOUR_OF_DAY, 14);
        periodoAlmoco.getHoraFimCalendar().set(Calendar.MINUTE, 00);
        diasSemana = new ArrayList<>();
        diasSemana.add(DayOfWeek.MONDAY);
        diasSemana.add(DayOfWeek.TUESDAY);
        diasSemana.add(DayOfWeek.WEDNESDAY);
        diasSemana.add(DayOfWeek.THURSDAY);
        diasSemana.add(DayOfWeek.FRIDAY);
        diasSemana.add(DayOfWeek.SATURDAY);
        periodoAlmoco.setDiasSemana(diasSemana);
        atuacao.setHorariosAlmoco(new ArrayList<PeriodoTempo>());
        atuacao.getHorariosAlmoco().add(periodoAlmoco);

        agenda.setHorariosAtendimento(horariosAtendimento);

        return atuacao;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcao_unica_simple, menu);
        MenuItem menuItem = menu.getItem(0);
        menuItem.setTitle(getText(R.string.acao_selecionar_data));
        menuItem.setIcon(R.drawable.agenda_branco);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.acao_unica:
                definirDataBusca();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Executa o processo de seleção de data para a marcar uma consulta através da seleção no calendário de um dia específico, redefinindo a lista de dias visíveis.
     */
    public void definirDataBusca() {
        final Calendar dataSelecionada = paginadorDiasAdapter.getDataSelecionada(paginadorDiasView);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                dataSelecionada.set(Calendar.YEAR, ano);
                dataSelecionada.set(Calendar.MONTH, mes);
                dataSelecionada.set(Calendar.DAY_OF_MONTH, dia);

                paginadorDiasAdapter = new PaginadorDiasAdapter(getSupportFragmentManager(), permiteVerPassado, dataSelecionada, atuacao, tipoAgenda);
                paginadorDiasView.setAdapter(paginadorDiasAdapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(paginadorDiasView);

                paginadorDiasView.setCurrentItem(paginadorDiasAdapter.getItemPosition(dataSelecionada));
            }
        }, dataSelecionada.get(Calendar.YEAR), dataSelecionada.get(Calendar.MONTH), dataSelecionada.get(Calendar.DAY_OF_MONTH));

        if(!permiteVerPassado) {
            datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        }

        datePickerDialog.show();
    }


}
