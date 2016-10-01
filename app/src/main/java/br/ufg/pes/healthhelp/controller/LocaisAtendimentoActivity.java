package br.ufg.pes.healthhelp.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.ufg.br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;
import br.ufg.pes.healthhelp.R;

public class LocaisAtendimentoActivity extends AppCompatActivity {
    private LocalAtendimentoDAO localAtendimentoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais_atendimento);
        initToolbar();
        localAtendimentoDAO = new LocalAtendimentoDAO();
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
