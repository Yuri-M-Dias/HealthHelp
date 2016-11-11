package br.ufg.inf.pes.healthhelp.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;
import br.ufg.inf.pes.healthhelp.service.LocalAtendimentoService;
import br.ufg.pes.healthhelp.R;

public class FormularioLocalAtendimentoActivity extends AppCompatActivity {

    private static final String TAG = FormularioLocalAtendimentoActivity.class.getCanonicalName();

    private LocalAtendimento localAtendimento;
    private LocalAtendimentoService localAtendimentoService;

    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private ViewGroup containerLocaisAtendimento;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_local_atendimento);

        EventBus.getDefault().register(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_formulario_novo_local_atendimento);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        campoNome = (EditText) findViewById(R.id.edit_nome_local);
        campoEndereco = (EditText) findViewById(R.id.edit_endereco);
        campoTelefone = (EditText) findViewById(R.id.edit_telefone);

        containerLocaisAtendimento = (ViewGroup) findViewById(R.id.container_horarios_atendimento);

        localAtendimentoService = new LocalAtendimentoService();

        localAtendimento = (LocalAtendimento) getIntent().getSerializableExtra(LocalAtendimentoActivity.LOCAL_ATENDIMENTO_INTENT_PARAMETER);
        if(localAtendimento == null) {
            localAtendimento = new LocalAtendimento();
        } else {
            preencherView();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcao_unica_simple, menu);

        MenuItem salvarMenuItem = menu.findItem(R.id.acao_unica);
        salvarMenuItem.setTitle(R.string.acao_salvar);
        salvarMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                salvar();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void preencherView(){
        getSupportActionBar().setTitle("Editar " + localAtendimento.getNome());

        campoNome.setText(localAtendimento.getNome());
        campoEndereco.setText(localAtendimento.getEndereco());
        campoTelefone.setText(localAtendimento.getTelefone());

        for(PeriodoTempo periodoTempo: localAtendimento.getHorariosAtendimento()){
            adicionarHorarioAtendimento(new PeriodoTempoView(this,
                    periodoTempo,
                    containerLocaisAtendimento));
        }
    }

    public void adicionarHorarioAtendimentoAcaoBotao(View view) {
        adicionarHorarioAtendimento(new PeriodoTempoView(this,
                new PeriodoTempo(),
                containerLocaisAtendimento));
    }

    public void adicionarHorarioAtendimento(PeriodoTempoView periodoTempoView){
        containerLocaisAtendimento.addView(periodoTempoView);
    }

    public void salvar(){
        View foco = null;

        String nome = campoNome.getText().toString();
        String endereco = campoEndereco.getText().toString();
        String telefone = campoTelefone.getText().toString();

        if(TextUtils.isEmpty(nome)) {
            campoNome.setError(getString(R.string.erro_campo_obrigatorio));
            foco = campoNome;
        } else if(TextUtils.isEmpty(endereco)) {
            campoEndereco.setError(getString(R.string.erro_campo_obrigatorio));
            foco = campoEndereco;
        } else if (TextUtils.isEmpty(telefone)) {
            campoTelefone.setError(getString(R.string.erro_campo_obrigatorio));
            foco = campoTelefone;
        } else if(!horariosAtendimentoSaoValidos()){
            foco = getCurrentFocus();
        }

        if(foco == null) {
            localAtendimento.setNome(nome);
            localAtendimento.setEndereco(endereco);
            localAtendimento.setTelefone(telefone);
            localAtendimento.setHorariosAtendimento(new ArrayList<PeriodoTempo>());

            for(int contadorPeriodosTempo = 0; contadorPeriodosTempo < containerLocaisAtendimento.getChildCount(); contadorPeriodosTempo++) {
                PeriodoTempoView periodoTempoView = (PeriodoTempoView) containerLocaisAtendimento.getChildAt(contadorPeriodosTempo);
                localAtendimento.getHorariosAtendimento().add(periodoTempoView.getPeriodoTempo());
            }
            localAtendimentoService.salvar(localAtendimento);
            progressDialog = ProgressDialog.show(this, getString(R.string.titulo_autenticando), getString(R.string.mensagem_autenticando), true, true);

        } else {
            foco.requestFocus();
        }
    }

    private boolean horariosAtendimentoSaoValidos(){
        boolean resultado = true;
        for(int contadorPeriodosTempo = 0; contadorPeriodosTempo < containerLocaisAtendimento.getChildCount(); contadorPeriodosTempo++) {
            PeriodoTempoView periodoTempoView = (PeriodoTempoView) containerLocaisAtendimento.getChildAt(contadorPeriodosTempo);
            if(!periodoTempoView.validarFormulario()) {
                resultado = false;
                break;
            }
        }

        //TODO: Verificar se diferentes horários de atendimento chocam horários. Em caso afirmativo, deve-se informar o erro e retornar false.

        return resultado;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDatabaseEvent(DatabaseEvent<String> databaseEvent) {
        progressDialog.dismiss();
        Toast.makeText(this, databaseEvent.getObjeto(), Toast.LENGTH_SHORT).show();
        finish();
    }
}
