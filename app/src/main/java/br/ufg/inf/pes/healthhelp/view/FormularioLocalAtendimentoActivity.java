package br.ufg.inf.pes.healthhelp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.pes.healthhelp.R;

public class FormularioLocalAtendimentoActivity extends AppCompatActivity {

    private static final String TAG = FormularioLocalAtendimentoActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_local_atendimento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "criando menu");
        getMenuInflater().inflate(R.menu.menu_so_salvar, menu);

        MenuItem salvarMenuItem = menu.findItem(R.id.acao_salvar);
        salvarMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                salvar();
                return true;
            }
        });
        return true;
    }

    public void adicionarHorarioAtendimentoAcaoBotao(View view) {
        adicionarHorarioAtendimento(new PeriodoTempoView(this,
                new PeriodoTempo(),
                (LinearLayout) findViewById(R.id.container_horarios_atendimento)));
    }

    public void adicionarHorarioAtendimento(PeriodoTempoView periodoTempoView){
        ((LinearLayout) findViewById(R.id.container_horarios_atendimento)).addView(periodoTempoView);
    }

    public void salvar(){
        Log.i(TAG, "salvando objeto");
    }
}
