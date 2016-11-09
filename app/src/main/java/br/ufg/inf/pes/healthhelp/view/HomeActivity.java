package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.ufg.pes.healthhelp.R;


public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

    }

    public void OnClick$VisitasMedicas(View view){

    }

    public void OnClick$Agendar(View view){

    }

    public void OnClick$VisualizarAgenda(View view){

    }

    public void OnClick$VisualizarAtuacao(View view){

    }

    public void OnClick$LocaisAtendimento(View view){

    }

}
