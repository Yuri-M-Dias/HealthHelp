package br.ufg.inf.pes.healthhelp.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.pes.healthhelp.R;

public class LocalAtendimentoActivity extends AppCompatActivity {
    public static final String LOCAL_ATENDIMENTO_INTENT_PARAMETER = "LOCAL_ATENDIMENTO";
    private LocalAtendimento localAtendimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_atendimento);
        localAtendimento = (LocalAtendimento) getIntent().getSerializableExtra(LOCAL_ATENDIMENTO_INTENT_PARAMETER);
        initToolbar();
        preencherView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
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
        TextView enderecoTextView = (TextView) findViewById(R.id.endereco_hospital);
        enderecoTextView.setText(enderecoTextView.getText() + localAtendimento.getEndereco());

        findViewById(R.id.icone_telefone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + localAtendimento.getTelefone()));
                startActivity(intent);
            }
        });
        TextView telefoneTextView = (TextView) findViewById(R.id.telefone_hospital);
        telefoneTextView.setText(telefoneTextView.getText() + localAtendimento.getTelefone());

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarHospital);
        toolbar.setTitle("");
        //TODO: Definir título, não subtítulo, pois este é menor. O título, entretanto, está com a cor escura.
        toolbar.setSubtitle(localAtendimento.getNome());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
