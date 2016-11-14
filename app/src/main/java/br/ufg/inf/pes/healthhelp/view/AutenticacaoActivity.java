package br.ufg.inf.pes.healthhelp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.ThrowableFailureEvent;

import br.ufg.inf.pes.healthhelp.model.Sessao;
import br.ufg.inf.pes.healthhelp.model.Usuario;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;
import br.ufg.inf.pes.healthhelp.service.AutenticacaoService;
import br.ufg.pes.healthhelp.R;

public class AutenticacaoActivity extends AppCompatActivity {

    private final String TAG = AutenticacaoActivity.class.getCanonicalName();

    private AutenticacaoService autenticacaoService;
    private ProgressDialog progressDialog;
    private EditText campoLogin;
    private EditText campoSenha;

    public AutenticacaoActivity() {
        autenticacaoService = new AutenticacaoService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autenticacao);

        campoLogin = (EditText) findViewById(R.id.autenticacao_campo_login);
        campoSenha = (EditText) findViewById(R.id.autenticacao_campo_senha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void autenticar(View view) {
        boolean cancelar = false;
        View foco = null;

        String login = campoLogin.getText().toString();
        String senha = campoSenha.getText().toString();

        if (TextUtils.isEmpty(login)) {
            campoLogin.setError(getString(R.string.erro_campo_obrigatorio));
            foco = campoLogin;
            cancelar = true;
        } else if (TextUtils.isEmpty(senha)) {
            campoSenha.setError(getString(R.string.erro_campo_obrigatorio));
            foco = campoSenha;
            cancelar = true;
        }

        if (cancelar) {
            foco.requestFocus();
        } else {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

    public void registrar(View view) {
        //TODO: Implementar quando a NovoUsuarioActivity estiver funcionando.
        Toast.makeText(this, "Não implementado ainda", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDatabaseEvent(DatabaseEvent<Usuario> databaseEvent) {
        progressDialog.dismiss();
        Toast.makeText(this, getString(R.string.mensagem_autenticacao_sucesso), Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Registrou autenticação? " + Sessao.criarSessao(this, databaseEvent.getObjeto()));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThrowableFailureEvent(ThrowableFailureEvent event) {
        progressDialog.dismiss();
        Toast.makeText(this, event.getThrowable().getMessage(), Toast.LENGTH_LONG).show();
    }
}
