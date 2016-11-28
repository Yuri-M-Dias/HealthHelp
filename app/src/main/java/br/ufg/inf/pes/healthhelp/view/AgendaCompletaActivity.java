package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;

public class AgendaCompletaActivity extends AgendaActivity<AgendaCompletaFragment> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntent().putExtra(AgendaActivity.ARG_PERMITE_PASSADO, true);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Agenda " + getAtuacao().getAgendas().get(0).getNome());
    }

}
