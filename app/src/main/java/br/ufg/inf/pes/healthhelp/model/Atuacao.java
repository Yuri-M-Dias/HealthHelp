package br.ufg.inf.pes.healthhelp.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Atuacao implements Serializable {

    @Exclude
    private String id;

    private Date dataInicio;
    private Date dataFim;

    private List<Agenda> agendas;
    private List<PeriodoTempo> horariosAlmoco;
    private LocalAtendimento localAtendimento;
    private Profissional profissional;

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public LocalAtendimento getLocalAtendimento() {
        return localAtendimento;
    }

    public void setLocalAtendimento(LocalAtendimento localAtendimento) {
        this.localAtendimento = localAtendimento;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
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

    public List<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<Agenda> agendas) {
        this.agendas = agendas;
    }

    public List<PeriodoTempo> getHorariosAlmoco() {
        return horariosAlmoco;
    }

    public void setHorariosAlmoco(List<PeriodoTempo> horariosAlmoco) {
        this.horariosAlmoco = horariosAlmoco;
    }
}
