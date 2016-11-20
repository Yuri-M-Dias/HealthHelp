package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import br.ufg.inf.pes.healthhelp.model.Usuario;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a um {@link Usuario}.
 */

public class UsuarioDAO extends AbstractDAO<Usuario> {
    public UsuarioDAO() {
        super(Usuario.class.getCanonicalName(), "usuario");
    }

    @Override
    public void buscarTodos() {
        //TODO
    }

    @Override
    public void buscarPelaId(String id) {
        //TODO
    }

    @Override
    public void inserir(Usuario objeto) {

    }

    @Override
    public void remover(Usuario objeto) {
        //TODO
    }

    @Override
    public void atualizar(Usuario objeto) {
        //TODO
    }

    public void inativar(Usuario usuario) {
        //TODO
    }

    public void buscarPorLogin(final String login, final String senha) {
        //TODO: Substituir a implementação abaixo pela implementação utilizando Firebase
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int contador = 0; contador < 3; contador++) {
                    Log.i(TAG, "Contagem regressiva para autenticacao: " + (3 - contador));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Ops... Erro no Thread.sleep: " + e.getMessage());
                    }
                }
                final Usuario usuario = new Usuario();
                usuario.setId("123");
                usuario.setNome("Cleber Alcântara");
                usuario.setLogin("cleber");
                usuario.setSenha("cleber");

                AsyncExecutor.create().execute(
                    new AsyncExecutor.RunnableEx() {
                        @Override
                        public void run() throws Exception {
                            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                                EventBus.getDefault().post(new DatabaseEvent<>(usuario));
                            } else {
                                throw new Exception("Usuário e/ou senha inválidos");
                            }
                        }
                    }
                );
            }
        }).start();
    }

}
