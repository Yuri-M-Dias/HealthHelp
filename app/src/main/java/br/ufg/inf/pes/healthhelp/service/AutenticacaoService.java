package br.ufg.inf.pes.healthhelp.service;

import br.ufg.inf.pes.healthhelp.dao.UsuarioDAO;

/**
 * Esta classe é responsável por prover serviços relacionados a autenticação de usuário.
 */

public class AutenticacaoService {
    UsuarioDAO usuarioDAO;

    public AutenticacaoService() {
        usuarioDAO = new UsuarioDAO();
    }

    public void autenticar(String login, String senha) {
        usuarioDAO.buscarPorLogin(login, senha);
    }
}
