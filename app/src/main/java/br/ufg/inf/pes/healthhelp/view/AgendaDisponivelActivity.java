package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AgendaDisponivelActivity extends AgendaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIntent().putExtra(AgendaActivity.ARG_PERMITE_PASSADO, false);
        getIntent().putExtra(AgendaActivity.ARG_TIPO_AGENDA, AgendaDisponivelFragment.class);
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
