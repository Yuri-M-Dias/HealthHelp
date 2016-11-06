package br.ufg.inf.pes.healthhelp.model.event;

/**
 * Esta classe é responsável por definir eventos criados por operações do banco de dados.
 */

public class DatabaseEvent<T> {
    private T objeto;

    public DatabaseEvent(T objeto) {
        this.objeto = objeto;
    }

    public T getObjeto() {
        return objeto;
    }

    public void setObjeto(T objeto) {
        this.objeto = objeto;
    }
}
