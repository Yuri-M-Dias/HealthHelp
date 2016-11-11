package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.pes.healthhelp.R;

public class LocalAtendimentoActivity extends AppCompatActivity {
    public static final String LOCAL_ATENDIMENTO_INTENT_PARAMETER = "LOCAL_ATENDIMENTO";
    private LocalAtendimento localAtendimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_atendimento);
        localAtendimento = (LocalAtendimento) getIntent().getSerializableExtra(LOCAL_ATENDIMENTO_INTENT_PARAMETER);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_local_atendimento);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(localAtendimento.getNome());

        preencherView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcao_unica_simple, menu);

        MenuItem salvarMenuItem = menu.findItem(R.id.acao_unica);
        salvarMenuItem.setTitle("Editar");
        salvarMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                editar();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editar() {
        Intent formularioLocalAtendimento = new Intent(this, FormularioLocalAtendimentoActivity.class);
        formularioLocalAtendimento.putExtra(LOCAL_ATENDIMENTO_INTENT_PARAMETER,
            localAtendimento);
        startActivity(formularioLocalAtendimento);
    }

    private void preencherView() {
        findViewById(R.id.icone_endereco).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String map = "http://maps.google.co.in/maps?q=" + localAtendimento.getEndereco();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                startActivity(intent);
            }
        });
        ((TextView) findViewById(R.id.endereco_hospital)).setText(localAtendimento.getEndereco());

        findViewById(R.id.icone_telefone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + localAtendimento.getTelefone()));
                startActivity(intent);
            }
        });
        ((TextView) findViewById(R.id.telefone_hospital)).setText(localAtendimento.getTelefone());

        String horariosAtendimentoLegivel = "";
        for (PeriodoTempo periodoTempo : localAtendimento.getHorariosAtendimento()) {
            horariosAtendimentoLegivel += periodoTempo.toString() + "\n";
        }
        ((TextView) findViewById(R.id.agendamento_hospital)).setText(horariosAtendimentoLegivel);

    }
}
