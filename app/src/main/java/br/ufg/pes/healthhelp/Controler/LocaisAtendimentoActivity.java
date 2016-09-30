package br.ufg.pes.healthhelp.Controler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.ufg.br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDao;
import br.ufg.br.ufg.inf.pes.healthhelp.dao.localAtendimentoDao;
import br.ufg.pes.healthhelp.R;

public class LocaisAtendimentoActivity extends AppCompatActivity {
    private LocalAtendimentoDao localAtendimentoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais_atendimento);
        initToolbar();
        localAtendimentoDao = new LocalAtendimentoDao();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarLocaisAtendimento);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hospital(View view){
        Intent intent = new Intent(this, HospitalActivity.class);
        startActivity(intent);
    }

    public void novoLocal(View view){
        Intent intent = new Intent(this, NovoLocalAtendimentoActivity.class);
        startActivity(intent);
    }
}
