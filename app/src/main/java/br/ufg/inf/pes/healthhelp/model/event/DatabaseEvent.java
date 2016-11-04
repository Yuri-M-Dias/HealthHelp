package br.ufg.inf.pes.healthhelp.model.event;

/**
 * Esta classe é responsável por definir eventos criados por operações do banco de dados.
 * Created by cleber on 04/11/16.
 */

public class DatabaseEvent<T> {
    private T objeto;
    private Exception excecao;

    public DatabaseEvent(T objeto) {
        this.objeto = objeto;
    }

    public DatabaseEvent(Exception excecao) {
        this.excecao = excecao;
    }

    public T getObjeto() {
        return objeto;
    }

    public void setObjeto(T objeto) {
        this.objeto = objeto;
    }

    public Exception getExcecao() {
        return excecao;
    }

    public void setExcecao(Exception excecao) {
        this.excecao = excecao;
    }
}
