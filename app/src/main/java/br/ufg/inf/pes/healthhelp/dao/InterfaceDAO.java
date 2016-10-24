package br.ufg.inf.pes.healthhelp.dao;

/**
 * Created by deassisrosal on 10/18/16.
 */

public interface InterfaceDAO<T> {
    public void loadAll();

    public void loadById( String id );

    public void inserir( T object );

    public void remover( String id );

    public void alterar( String id , T object);
}
