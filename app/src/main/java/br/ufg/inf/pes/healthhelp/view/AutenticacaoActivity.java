package br.ufg.inf.pes.healthhelp.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.ufg.inf.pes.healthhelp.model.Sessao;
import br.ufg.inf.pes.healthhelp.model.Usuario;
import br.ufg.inf.pes.healthhelp.model.event.ExternalDatabaseEvent;
import br.ufg.inf.pes.healthhelp.service.AutenticacaoService;
import br.ufg.pes.healthhelp.R;

public class AutenticacaoActivity extends AppCompatActivity {

    private AutenticacaoService autenticacaoService;
    private ProgressDialog progressDialog;

    public AutenticacaoActivity(){
        autenticacaoService = new AutenticacaoService();
    }

    private EditText campoLogin;
    private EditText campoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autenticacao);

        campoLogin = (EditText) findViewById(R.id.login);
        campoSenha = (EditText) findViewById(R.id.senha);

        findViewById(R.id.botao_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticar();
            }
        });

        findViewById(R.id.botao_registrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void autenticar() {
        boolean cancelar = false;
        View foco = null;

        String login = campoLogin.getText().toString();
        String senha = campoSenha.getText().toString();

        if(TextUtils.isEmpty(login)) {
            campoLogin.setError(getString(R.string.erro_campo_obrigatorio));
            foco = campoLogin;
            cancelar = true;
        } else if (TextUtils.isEmpty(senha)) {
            campoSenha.setError(getString(R.string.erro_campo_obrigatorio));
            foco = campoSenha;
            cancelar = true;
        }

        if(cancelar) {
            foco.requestFocus();
        } else {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            autenticacaoService.autenticar(login, senha);
            progressDialog = ProgressDialog.show(this, getString(R.string.titulo_autenticando), getString(R.string.mensagem_autenticando), true, true);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void registrar() {
        Toast.makeText(this, "Não implementado ainda", Toast.LENGTH_SHORT).show();
        //TODO: Implementar quando a NovoUsuarioActivity estiver funcionando.
    }

    @Subscribe
    public void processarResultadoAutenticacaoBD(final ExternalDatabaseEvent<Usuario> databaseEvent) {
        progressDialog.dismiss();
        final AutenticacaoActivity autenticacaoActivity = this;
        if(databaseEvent.getObjeto() == null) {
            new Thread() {
                public void run() {
                    autenticacaoActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(autenticacaoActivity, databaseEvent.getExcecao().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }.start();
        } else {
            Log.i("Autenticacao", "Autenticou? " + Sessao.criarSessao(this, databaseEvent.getObjeto()));
            finish();
        }
    }
}
