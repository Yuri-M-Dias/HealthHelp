package br.ufg.inf.pes.healthhelp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class Sessao {
    private static String CHAVE_ARQUIVO_SESSAO = "sessao";
    private static String CHAVE_PREFERENCIA_USUARIO = "usuario";

    /**
     * Obtém o usuário autenticado no aplicativo.
     * @param contexto Contexto necessário para manipulação dos dados da sessão.
     * @return Usuário que está atualmente autenticado ou NULL caso não haja usuário autenticado.
     */
    public static Usuario getUsuario(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(CHAVE_ARQUIVO_SESSAO, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CHAVE_PREFERENCIA_USUARIO, "");
        Log.i("Sessao", "Usuario string: " + json);
        return gson.fromJson(json, Usuario.class);
    }

    /**
     * Atualiza o usuário que está autenticado no aplicativo.
     * @param contexto Contexto necessário para manipulação dos dados da sessão.
     * @param usuario Usuário a se tornar autenticado no aplicativo.
     * @return TRUE se a atualização ocontece corretamente ou FALSE caso contrário.
     */
    public static boolean atualizarUsuario(Context contexto, Usuario usuario) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(CHAVE_ARQUIVO_SESSAO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(usuario);
        editor.putString(CHAVE_PREFERENCIA_USUARIO, json);
        return editor.commit();
    }

    /**
     * Cria uma nova sessão para um usuário no aplicativo. Essa funcção tem o mesmo efeito de {@link Sessao#atualizarUsuario(Context, Usuario)}.
     * @param contexto Contexto necessário para manipulação dos dados da sessão.
     * @param usuario Usuário a se tornar autenticado no aplicativo.
     * @return TRUE se a criação acontece corretamente ou FALSE caso contrário.
     */
    public static boolean criarSessao(Context contexto, Usuario usuario) {
        return atualizarUsuario(contexto, usuario);
    }

    /**
     * Finaliza uma sessão do usuário no aplicativo. Essa funcção tem o mesmo efeito de {@link Sessao#atualizarUsuario(Context, Usuario)} com o parâmetro "usuario" nulo.
     * @param contexto Contexto necessário para manipulação dos dados da sessão.
     * @return TRUE se a finalização acontece corretamente ou FALSE caso contrário.
     */
    public static boolean finalizarSessao(Context contexto){
        return atualizarUsuario(contexto, null);
    }

    /**
     * Verifica se a sessão do aplicativo está ativa.
     * @param contexto Contexto necessário para manipulação dos dados da sessão.
     * @return TRUE se a sessão está ativa ou FALSE caso não esteja.
     */
    public static boolean estaAtiva(Context contexto){
        return (getUsuario(contexto)!= null);
    }
}
