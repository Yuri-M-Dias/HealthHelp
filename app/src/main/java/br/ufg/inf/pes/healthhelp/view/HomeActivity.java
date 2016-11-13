package br.ufg.inf.pes.healthhelp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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

    }

    public void OnClick$VisitasMedicas(View view){

        Context context = getApplicationContext();
        CharSequence text = "Visualizar visitas medicas nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

    public void OnClick$Agendar(View view){

        Context context = getApplicationContext();
        CharSequence text = "Agendar visitas medicas nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

    public void OnClick$VisualizarAgenda(View view){

        Context context = getApplicationContext();
        CharSequence text = "Visualizar agenda nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

    public void OnClick$VisualizarAtuacao(View view){

        Context context = getApplicationContext();
        CharSequence text = "Visualizar atuação nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

    public void OnClick$LocaisAtendimento(View view){

        Context context = getApplicationContext();
        CharSequence text = "Visualizar locais de atendimento nao implementado";
        MensagemNaoImplementado.MostraMensagemNaoImplementado(context, text);
    }

}
