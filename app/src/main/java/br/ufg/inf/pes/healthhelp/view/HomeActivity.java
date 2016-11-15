package br.ufg.inf.pes.healthhelp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import br.ufg.inf.pes.healthhelp.model.Sessao;
import br.ufg.inf.pes.healthhelp.service.MensagemNaoImplementado;
import br.ufg.pes.healthhelp.R;


public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_homeactivity);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart(){
        super.onStart();

        if(!Sessao.estaAtiva(this)){
            Intent intent = new Intent(this, AutenticacaoActivity.class);
            startActivity(intent);
        }
    }

    public void visitasMedicas(View view){

        Context context = getApplicationContext();
        CharSequence text = "Visualizar visitas medicas nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

    public void agendarVisitasMedicas(View view){

        Context context = getApplicationContext();
        CharSequence text = "Agendar visitas medicas nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

    public void visualizarAgenda(View view){

        Context context = getApplicationContext();
        CharSequence text = "Visualizar agenda nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

    public void visualizarAtuacao(View view){

        Context context = getApplicationContext();
        CharSequence text = "Visualizar atuação nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

    public void visualizarLocaisAtendimento(View view){

        Intent intent = new Intent(this, LocaisAtendimentoActivity.class);
        startActivity(intent);
    }

}
