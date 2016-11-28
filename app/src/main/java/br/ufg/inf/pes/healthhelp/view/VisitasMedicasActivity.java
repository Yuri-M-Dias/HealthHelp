package br.ufg.inf.pes.healthhelp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.ufg.pes.healthhelp.R;


public class VisitasMedicasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitas_medicas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_visitas_medicas);
        setSupportActionBar(toolbar);

    }
}
