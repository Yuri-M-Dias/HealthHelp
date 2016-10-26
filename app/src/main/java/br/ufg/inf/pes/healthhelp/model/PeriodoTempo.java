package br.ufg.inf.pes.healthhelp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class PeriodoTempo implements Serializable {
    private String horaInicio;
    private String horaFim;
    private String dataInicio; // dateFormat no estilo dd-MM-yyyy
    private String dataFim; // dateFormat no estilo dd-MM-yyyy
    private String diasSemana;

    public PeriodoTempo() {
    }

    public PeriodoTempo(String horaInicio, String horaFim, String dataInicio, String dataFim, String diasSemana) {
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.diasSemana = diasSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public String getDiasSemana() {

        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public void setHoraFim(String horaFim) {

        this.horaFim = horaFim;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }




}
