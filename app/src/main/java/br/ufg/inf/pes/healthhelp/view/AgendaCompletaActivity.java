package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;

public class AgendaCompletaActivity extends AgendaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntent().putExtra(AgendaActivity.ARG_PERMITE_PASSADO, true);
        getIntent().putExtra(AgendaActivity.ARG_TIPO_AGENDA, AgendaCompletaFragment.class);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Agenda " + getAtuacao().getAgendas().get(0).getNome());
    }


}
