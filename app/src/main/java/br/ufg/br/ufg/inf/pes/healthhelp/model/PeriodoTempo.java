package br.ufg.br.ufg.inf.pes.healthhelp.model;

import java.util.Date;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class PeriodoTempo {
    private String horaInicio;
    private String horaFim;
    private Date dataInicio;
    private Date dataFim;
    private String diasSemana;


    public PeriodoTempo(String horaInicio, String horaFim, Date dataInicio, Date dataFim, String diasSemana) {
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.diasSemana = diasSemana;
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
