package br.ufg.inf.pes.healthhelp.model.event;

/**
 * Esta classe é responsável por definir eventos criados por operações do banco de dados.
 * @param <T> Tipo de objeto esperado a ser retornado pelo banco de dados.
 */
public class DatabaseEvent<T> {
    private T objeto;

    /**
     * Cria um evento de banco de dados.
     * @param objeto Objeto a ser retornado para os inscritos no evento.
     */
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
