package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.UsuarioDAO;
import br.ufg.inf.pes.healthhelp.model.Usuario;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link Usuario}.
 * Created by cleber on 02/11/16.
 */

public class UsuarioService {
    UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    public void salvar(Usuario usuario) {

        if(usuario.getId() == null) {
            usuarioDAO.inserir(usuario);
        } else {
            usuarioDAO.atualizar(usuario);
        }
    }

    public void inativar(Usuario usuario) {
        usuarioDAO.inativar(usuario);
    }
}
