package br.ufg.inf.pes.healthhelp.model.event;

import java.util.Calendar;

public class AtendimentoEvent<T> extends DatabaseEvent<T> {

    private Calendar diaOcorrencia;

    public AtendimentoEvent(T objeto, Calendar diaOcorrencia) {
        super(objeto);
        this.diaOcorrencia = diaOcorrencia;
    }

    public Calendar getDiaOcorrencia() {
        return diaOcorrencia;
    }

    public void setDiaOcorrencia(Calendar diaOcorrencia) {
        this.diaOcorrencia = diaOcorrencia;
    }
}
