package br.ufg.inf.pes.healthhelp.model;

import java.util.List;

/**
 * Created by deassisrosal on 10/30/16.
 */

public class Agenda {
    private String id;
    private String nome;
    private int tempoPadrao;
    private List<PeriodoTempo> horariosAtendimento;
    private  List<PeriodoTempo> horariosBloqueados;

    public List<PeriodoTempo> getHorariosBloqueados() {
        return horariosBloqueados;
    }

    public void setHorariosBloqueados(List<PeriodoTempo> horariosBloqueados) {
        this.horariosBloqueados = horariosBloqueados;
    }

    public List<PeriodoTempo> getHorariosAtendimento() {
        return horariosAtendimento;
    }

    public void setHorariosAtendimento(List<PeriodoTempo> horariosAtendimento) {
        this.horariosAtendimento = horariosAtendimento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTempoPadrao() {
        return tempoPadrao;
    }

    public void setTempoPadrao(int tempoPadrao) {
        this.tempoPadrao = tempoPadrao;
    }
}
