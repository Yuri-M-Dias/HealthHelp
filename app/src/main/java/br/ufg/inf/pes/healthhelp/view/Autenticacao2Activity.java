package br.ufg.inf.pes.healthhelp.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.ufg.pes.healthhelp.R;

public class Autenticacao2Activity extends AppCompatActivity {

    private AutenticacaoService autenticacaoService;

    public Autenticacao2Activity(){
        autenticacaoService = new AutenticacaoService();
    }

    private EditText campoLogin;
    private EditText campoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao2);

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
        autenticacaoService.autenticar(campoLogin.getText(), campoSenha.getText());
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
