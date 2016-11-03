package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.Usuario;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a um {@link Usuario}.
 * Created by cleber on 03/11/16.
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
        //TODO
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

    public void buscarPorLogin(String login, String senha) {
        //TODO
    }
}
