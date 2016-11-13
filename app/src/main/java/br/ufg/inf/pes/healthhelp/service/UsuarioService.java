package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.UsuarioDAO;
import br.ufg.inf.pes.healthhelp.model.Usuario;

/**
 * Esta classe é responsável por prover serviços relacionados a uma {@link Usuario}.
 */

public class UsuarioService {
    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    public void salvar(Usuario usuario) {

        if (usuario.getId() == null) {
            usuarioDAO.inserir(usuario);
        } else {
            usuarioDAO.atualizar(usuario);
        }
    }

    public void inativar(Usuario usuario) {
        usuarioDAO.inativar(usuario);
    }
}
