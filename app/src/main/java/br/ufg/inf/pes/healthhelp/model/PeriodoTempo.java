package br.ufg.inf.pes.healthhelp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class PeriodoTempo implements Serializable {
    private String horaInicio;
    private String horaFim;
    private Date dataInicio;
    private Date dataFim;
    private String diasSemana;

    public PeriodoTempo() {
    }

    public PeriodoTempo(String horaInicio, String horaFim, Date dataInicio, Date dataFim, String diasSemana) {
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }




}
