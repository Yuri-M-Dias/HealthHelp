package br.ufg.inf.pes.healthhelp.dao;

public interface InterfaceDAO<T> {

    public void buscarTodos();

    public void buscarPelaId(String id);

    public void inserir(T objeto);

    public void remover(T objeto);

    public void atualizar(T objeto);
}
