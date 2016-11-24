package br.ufg.inf.pes.healthhelp.model.event;

import java.util.Calendar;

/**
 * Esta classe é responsável por definir eventos criados por operações do banco de dados que lidam requisições concorrentes que tem um objeto do tipo {@link Calendar} como filtro.
 */
public class PaginadorDiasEvent<T> extends DatabaseEvent<T> {

    private Calendar filtro;

    /**
     * Cria um evento de paginador de dias.
     *
     * @param objeto Objeto a ser retornado para os inscritos no evento.
     * @param filtro Data a ser utilizada como filtro para que apenas as páginas corretas tratem o objeto retornado a elas.
     */
    public PaginadorDiasEvent(T objeto, Calendar filtro) {
        super(objeto);
        this.filtro = filtro;
    }

    public Calendar getFiltro() {
        return filtro;
    }

    public void setFiltro(Calendar filtro) {
        this.filtro = filtro;
    }
}
