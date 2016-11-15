package br.ufg.inf.pes.healthhelp.model;

import com.google.firebase.database.Exclude;

import java.util.List;


public class Agenda {
    @Exclude
    private String id;
    private String nome;
    private int tempoPadrao;
    private List<PeriodoTempo> horariosAtendimento;
    private  List<PeriodoTempo> horariosBloqueados;

    public Agenda() {
    }

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

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
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
