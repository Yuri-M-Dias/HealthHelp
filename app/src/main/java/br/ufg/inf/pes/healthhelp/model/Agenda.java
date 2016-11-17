package br.ufg.inf.pes.healthhelp.model;

import com.google.firebase.database.Exclude;

import java.util.List;


public class Agenda {
    @Exclude
    private String id;
    private String nome;
    private int tempoPadraoMinutos;
    private List<PeriodoTempo> horariosAtendimento;
    private List<PeriodoTempo> horariosBloqueados;

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

    public int getTempoPadraoMinutos() {
        return tempoPadraoMinutos;
    }

    public void setTempoPadraoMinutos(int tempoPadraoMinutos) {
        this.tempoPadraoMinutos = tempoPadraoMinutos;
    }

}
