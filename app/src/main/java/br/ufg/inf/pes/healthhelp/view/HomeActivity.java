package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.ufg.inf.pes.healthhelp.util.Sessao;
import br.ufg.pes.healthhelp.R;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_homeactivity);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Sessao.estaAtiva(this)) {
            Intent intent = new Intent(this, AutenticacaoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homeactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void visualizarVisitasMedicas(View view) {
        CharSequence text = "Visualizar visitas medicas nao implementado";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void agendarVisitaMedica(View view) {
        Intent intent = new Intent(this, NovoAtendimentoActivity.class);
        startActivity(intent);
    }

    public void visualizarAgendas(View view) {

        Intent intent = new Intent(this, AgendasActivity.class);
        intent.putExtra(AgendasActivity.ARG_PROFISSIONAL, (Serializable[]) null);
        startActivity(intent);
    }

    public void visualizarAtuacoes(View view) {
        Intent intent = new Intent(this, AtuacoesActivity.class);
        startActivity(intent);
    }

    public void visualizarLocaisAtendimento(View view) {
        Intent intent = new Intent(this, LocaisAtendimentoActivity.class);
        startActivity(intent);
    }

    private void logout() {
        Sessao.finalizarSessao(this);
        Intent intent = new Intent(this, AutenticacaoActivity.class);
        startActivity(intent);
    }

}
