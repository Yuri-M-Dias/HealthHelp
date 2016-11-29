package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

public class DetalhaAtendimentoActivity extends AppCompatActivity {

    public static final String ARG_ATENDIMENTO = "atendimento";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalha_atendimento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalhe_de_atendimento);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    //TODO: Remover a implementação abaixo quando as classes que chamam essa aqui estiverem prontas.
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


    void selecionarHorarioAtendimento(View view) {
        Atuacao atuacao = criarAtuacao();
        Intent agendaDisponivelIntent = new Intent(this, AgendaDisponivelActivity.class);
        agendaDisponivelIntent.putExtra(AgendaActivity.ARG_ATUACAO, atuacao);
        startActivityForResult(agendaDisponivelIntent, AgendaActivity.SELECIONAR_HORARIO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        SimpleDateFormat dataFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaFormatter = new SimpleDateFormat("HH:mm");
        Atendimento atendimento = (Atendimento) data.getSerializableExtra(AgendaActivity.ARG_ATENDIMENTO_AGENDADO);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dataFormatter.format(atendimento.getHoraInicioCalendar().getTime()));
        stringBuilder.append("\ndas ");
        stringBuilder.append(horaFormatter.format(atendimento.getHoraInicioCalendar().getTime()));
        stringBuilder.append(" às ");
        stringBuilder.append(horaFormatter.format(atendimento.getHoraFimCalendar().getTime()));

        ((TextView) findViewById(R.id.momento_atendimento)).setText(stringBuilder.toString());

        Log.i(getClass().getCanonicalName(), "Atendimento carregado com sucesso!");
        super.onActivityResult(requestCode, resultCode, data);
    }

}
