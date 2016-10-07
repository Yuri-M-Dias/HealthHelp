package br.ufg.inf.pes.healthhelp.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.service.LocalAtendimentoService;
import br.ufg.pes.healthhelp.R;

public class LocaisAtendimentoActivity extends AppCompatActivity {
    private LocalAtendimentoService localAtendimentoService;

    ArrayAdapter<LocalAtendimento> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais_atendimento);
        initToolbar();
        loadLocaisAtendimento();
    }

    private void loadLocaisAtendimento() {
        adapter = new ArrayAdapter<LocalAtendimento>(this, android.R.layout.simple_list_item_1, R.id.list_locais_atendimento,
                localAtendimentoService.obterLocaisAtendimento());
        ListView locaisAtendimentoView = (ListView) findViewById(R.id.list_locais_atendimento);
        locaisAtendimentoView.setAdapter(adapter);
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
