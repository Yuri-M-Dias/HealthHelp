package br.ufg.inf.pes.healthhelp.model.event;

/**
 * Created by cleber on 04/11/16.
 */

public class ExternalDatabaseEvent<T> extends DatabaseEvent<T> {
    public ExternalDatabaseEvent(T objeto) {
        super(objeto);
    }

    public ExternalDatabaseEvent(Exception excecao) {
        super(excecao);
    }
}
