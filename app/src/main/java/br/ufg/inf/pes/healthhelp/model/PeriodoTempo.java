package br.ufg.inf.pes.healthhelp.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;

public class PeriodoTempo implements Serializable {

    private Calendar horaInicio;
    private Calendar horaFim;
    private Calendar dataInicio;
    private Calendar dataFim;

    private List<DayOfWeek> diasSemana;

    public PeriodoTempo() {
    }

    public PeriodoTempo(Calendar horaInicio, Calendar horaFim, Calendar dataInicio, Calendar dataFim, List<DayOfWeek> diasSemana) {
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.diasSemana = diasSemana;
    }

    public Calendar getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Calendar horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Calendar getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Calendar horaFim) {
        this.horaFim = horaFim;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }

    public List<DayOfWeek> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<DayOfWeek> diasSemana) {
        this.diasSemana = diasSemana;
    }

}
