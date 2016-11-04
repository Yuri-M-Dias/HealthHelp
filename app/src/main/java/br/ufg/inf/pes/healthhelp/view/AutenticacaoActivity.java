package br.ufg.inf.pes.healthhelp.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        EventBus.getDefault().register(this);

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

        progressDialog = new ProgressDialog(this);
    }

    private void autenticar() {
        autenticacaoService.autenticar(campoLogin.getText().toString(), campoSenha.getText().toString());
        Log.d("autenticacaoView", "depois de autenticar no serviço");
        progressDialog = ProgressDialog.show(this, "Autenticação",
                "Por favor, aguarde enquanto o sistema realiza sua autenticação...", true, true);
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
    public void processarResultadoAutenticacao(final ExternalDatabaseEvent<Usuario> databaseEvent) {
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
            Sessao.getInstance().setUsuario(databaseEvent.getObjeto());
            finish();
        }
    }

    //TODO: Revisar esse método para redirecionamento a partir de outras activities.
    private void redirecionar(Activity activity, Bundle extras) {
        Intent intent = new Intent(this, Activity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
