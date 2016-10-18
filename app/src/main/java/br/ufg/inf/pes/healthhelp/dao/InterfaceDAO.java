package br.ufg.inf.pes.healthhelp.dao;

/**
 * Created by deassisrosal on 10/18/16.
 */

public interface InterfaceDAO {
    public void loadAll();

    public void loadById( String id );

    public void inserir( Object T );

    public void remover( String id );

    public void alterar( String id , Object T);
}
