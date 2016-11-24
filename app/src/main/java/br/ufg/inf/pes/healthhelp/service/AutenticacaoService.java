package br.ufg.inf.pes.healthhelp.service;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.ufg.inf.pes.healthhelp.dao.UsuarioDAO;

/**
 * Esta classe é responsável por prover serviços relacionados a autenticação de usuário.
 */

public class AutenticacaoService {
    private static final String TAG = AutenticacaoService.class.getCanonicalName();
    private UsuarioDAO usuarioDAO;


    public AutenticacaoService() {
        usuarioDAO = new UsuarioDAO();
    }

    /**
     * Codifica uma senha para propósitos como ocultação de informação e armazenamento seguro no banco de dados.
     *
     * @param senha Senha a ser codificada.
     * @return senha codificada, em caso de sucesso, ou uma string vazia caso não seja possível codificar a senha.
     */
    public static String codificarSenha(String senha) {
        String senhaCodificada = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(senha.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            senhaCodificada = number.toString(16);

            while (senhaCodificada.length() < 32) {
                senhaCodificada = "0" + senhaCodificada;
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Não foi possível codificar a senha informada. A senha codificada será vazia! Erro: " + e.getMessage());
        }
        return senhaCodificada;
    }

    public void autenticar(String login, String senha) {
        usuarioDAO.buscarPorLogin(login, senha);
    }
}
