package br.ufg.inf.pes.healthhelp.dao;

/**
 * Created by deassisrosal on 10/18/16.
 */

public interface InterfaceDAO<T> {

    public void carregarTodos();

    public void carregarPelaId(int id );

    public void inserir( T objeto );

    public void remover( String id );

    public void atualizar(T objeto );
}
