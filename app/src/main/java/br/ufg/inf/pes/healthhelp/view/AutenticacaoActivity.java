package br.ufg.inf.pes.healthhelp.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.ufg.inf.pes.healthhelp.model.Usuario;
import br.ufg.inf.pes.healthhelp.service.AutenticacaoService;
import br.ufg.pes.healthhelp.R;

public class AutenticacaoActivity extends AppCompatActivity {

     private AutenticacaoService autenticacaoService;

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

    private void autenticar() {
        autenticacaoService.autenticar(campoLogin.getText().toString(), campoSenha.getText().toString());
        //TODO: Ativar carregamento

    }

    private void registrar() {
        Intent intent = new Intent(this, NovoUsuarioActivity.class);
        startActivity(intent);
    }

    private void processarResultadoAutenticacao(Usuario usuario) {
        if(usuario == null) {
            //TODO: Desativar carregamento
            Intent intent = new Intent(this, NovoUsuarioActivity.class);
            startActivity(intent);
        } else {
            //TODO: mostrar alerta de erro
        }
    }

    private void redirecionar(Activity activity, Bundle extras) {
        Intent intent = new Intent(this, Activity.class); //TODO: Review
        intent.putExtras(extras);
        startActivity(intent);
    }
}
