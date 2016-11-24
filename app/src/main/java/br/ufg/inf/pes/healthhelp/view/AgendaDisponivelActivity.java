package br.ufg.inf.pes.healthhelp.view;

import android.app.DatePickerDialog;
import android.database.DataSetObserver;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class AgendaDisponivelActivity extends AppCompatActivity {

    public static final String ARG_ATUACAO = "atuacao";

    private PaginadorDiasAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private Atuacao atuacao;

    private Calendar dataSelecionada;
    private Atendimento atendimento;
    private AtendimentoService atendimentoService;

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public AtendimentoService getAtendimentoService() {
        return atendimentoService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar contexto = Calendar.getInstance();

        atuacao = (Atuacao) getIntent().getSerializableExtra(ARG_ATUACAO);
        criarAtuacao(); //Remover essa chamada e o método quando a linhaa acima retornar algo válido.

        atendimentoService = new AtendimentoService();
        setContentView(R.layout.activity_agenda_disponivel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Selecionar horário de atendimento");

        mSectionsPagerAdapter = new PaginadorDiasAdapter(getSupportFragmentManager(), false, contexto, atuacao.getAgendas());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        mSectionsPagerAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                mViewPager.setAdapter(mSectionsPagerAdapter);
                int novaPosicao = mSectionsPagerAdapter.getItemPosition(mSectionsPagerAdapter.getDataMudanca());
                mViewPager.setCurrentItem(novaPosicao - 1);
                mViewPager.setCurrentItem(novaPosicao);
            }
        });

    }

    //TODO: Remover a implementação abaixo quando as classes que chamam essa aqui estiverem prontas.
    private void criarAtuacao() {
        atuacao = new Atuacao();
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

        agenda.setHorariosAtendimento(horariosAtendimento);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    public void concluirSelecaoAtendimento(){
        //TODO: Enviar retorno para activity que chamou essa. Referência: http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
        finish();
    }


    public void definirDataBusca(){
        if (dataSelecionada == null) {
            dataSelecionada = Calendar.getInstance();
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                dataSelecionada.set(Calendar.YEAR, i);
                dataSelecionada.set(Calendar.MONTH, i1);
                dataSelecionada.set(Calendar.DAY_OF_MONTH, i2);
                mSectionsPagerAdapter = new PaginadorDiasAdapter(getSupportFragmentManager(), false, dataSelecionada, atuacao.getAgendas());
                mViewPager.setAdapter(mSectionsPagerAdapter);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);
                mViewPager.setCurrentItem(mSectionsPagerAdapter.getItemPosition(dataSelecionada));
            }
        }, dataSelecionada.get(Calendar.YEAR), dataSelecionada.get(Calendar.MONTH), dataSelecionada.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }


}
