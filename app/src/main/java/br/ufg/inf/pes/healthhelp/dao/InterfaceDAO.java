package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.BaseObject;

/**
 * Essa interface define os métodos que são padrão para todos os objetos que são persistidos no banco de dados,
 *
 * @param <T> Tipo do objeto a ser manipulado.
 */
public interface InterfaceDAO<T extends BaseObject> {

    /**
     * Busca todos os objetos do tipo {@link T} no banco de dados.
     */
    void buscarTodos();

    /**
     * Busca um objeto do tipo {@link T} no banco de dados.
     *
     * @param id Identifcador do objeto a ser buscado.
     */
    void buscarPelaId(String id);

    /**
     * Insere um objeto do tipo {@link T} no banco de dados.
     *
     * @param objeto Objeto a ser inserido no banco de dados.
     */
    void inserir(T objeto);

    /**
     * Remove um objeto do tipo {@link T} no banco de dados.
     *
     * @param objeto Objeto a ser removido do banco de dados.
     */
    void remover(T objeto);

    /**
     * Atualiza um objeto do tipo {@link T} no banco de dados.
     *
     * @param objeto Objeto a ser atualizado do banco de dados.
     */
    void atualizar(T objeto);

}
