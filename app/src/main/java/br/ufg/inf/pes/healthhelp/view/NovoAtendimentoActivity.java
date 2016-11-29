package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.pes.healthhelp.R;

public class NovoAtendimentoActivity extends AppCompatActivity {

    private AutoCompleteTextView instituicao;
    private AutoCompleteTextView profissional;
    private Button dataHorario;
    private Atendimento atendimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_atendimento);
        initToolbar();

        instituicao = (AutoCompleteTextView) findViewById(R.id.autoCompleteLocalAtendimento);
        profissional = (AutoCompleteTextView) findViewById(R.id.autoCompleteProfissionalSaude);
        dataHorario = (Button) findViewById(R.id.button_dados_data_horario_atendimento);

        //essas variáveis serão instanciadas chamando os métodos: getNomesLocaisAtendimentos e getNomesProfissionais.
        String[] instituicoes = new String[]{"Hospital das Clínicas", "Hospinal Santa Genoveva",
            "Hospital HGG", "Hospital do Cancer", "Novo Hospital"};
        String[] profissionais = new String[]{"João não sei das quantas", "José Maria",
            "Açogueiro do pará", "Médico bom", "Enfermeiro"};

        instituicao.setAdapter(adapter(instituicoes));
        profissional.setAdapter(adapter(profissionais));
    }

    public ArrayAdapter<String> adapter(String[] listaDados) {
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
            android.R.layout.simple_dropdown_item_1line, listaDados);
        return adaptador;
    }

    public String[] getNomesLocaisAtendimentos(String dado) {
        //buscar no banco todos os nomes dos locais para ser usado pelo adapter do AutoCompleteTextView
        return null;
    }

    public String[] getNomesProfissionais(String dado) {
        //buscar no banco todos os nomes dos profissionais para ser usado pelo adapter do AutoCompleteTextView
        return null;
    }

    public void horarioAtendimento(View view) {
        Intent intent = new Intent(this, AgendaDisponivelActivity.class);
        intent.putExtra(AgendaActivity.ARG_ATUACAO, criarAtuacao());
        startActivityForResult(intent, AgendaActivity.SELECIONAR_HORARIO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AgendaActivity.SELECIONAR_HORARIO_REQUEST && resultCode == RESULT_OK) {
            SimpleDateFormat dataFormatter = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat horaFormatter = new SimpleDateFormat("HH:mm");
            Atendimento atendimento = (Atendimento) data.getSerializableExtra(AgendaActivity.ARG_ATENDIMENTO_AGENDADO);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dataFormatter.format(atendimento.getHoraInicioCalendar().getTime()));
            stringBuilder.append(", das ");
            stringBuilder.append(horaFormatter.format(atendimento.getHoraInicioCalendar().getTime()));
            stringBuilder.append(" às ");
            stringBuilder.append(horaFormatter.format(atendimento.getHoraFimCalendar().getTime()));

            dataHorario.setText(stringBuilder.toString());

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public String setHorarioAtendimento() {
        //SimpleDateFormat data = new SimpleDateFormat("HH:mm");
        //String horaData = "Das " + data.format(atendimento.getHoraInicio()) + " às " +
        //   data.format(atendimento.getHoraFim());
        return " ";
    }

    public void marcar() {
        CharSequence text = "Atendimento marcado com sucesso";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_novo_atendimento);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcao_unica_simple, menu);

        MenuItem marcarMenuItem = menu.findItem(R.id.acao_unica);
        marcarMenuItem.setTitle(R.string.acao_marcar);
        marcarMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                marcar();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

}
