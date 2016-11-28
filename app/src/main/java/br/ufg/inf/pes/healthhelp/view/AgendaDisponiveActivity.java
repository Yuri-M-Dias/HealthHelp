package br.ufg.inf.pes.healthhelp.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class AgendaDisponiveActivity extends AgendaDisponivelActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntent().putExtra(AgendaDisponivelActivity.ARG_TIPO_AGENDA, AgendaFragment.class);
        getIntent().putExtra(AgendaDisponivelActivity.ARG_PERMITE_PASSADO, false);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Selecionar horário de atendimento");
    }

    /**
     * Conclui o processo de seleção de um horário de atendimento para um agendamente, enviando um valor de retorno para a classe que chamou essa classe.
     */
    public void concluirSelecaoAtendimento() {
        Intent intent = new Intent();
        intent.putExtra(ARG_ATENDIMENTO_AGENDADO, getAtendimento());
        setResult(RESULT_OK, intent);
        finish();
    }
}
