package br.ufg.inf.pes.healthhelp.model;

import java.util.Date;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;

/**
 * Created by deassisrosal on 10/30/16.
 */

public class Atuacao {
    private String id;
    private Date dataInicioAtuacao;
    private Date dataFimAtuacao;

    private List<Agenda> agendas;
    private List<PeriodoTempo> horariosAlmoco;
    private LocalAtendimento localAtendimento;
    private Profissional profissional;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDataInicioAtuacao() {
        return dataInicioAtuacao;
    }

    public void setDataInicioAtuacao(Date dataInicioAtuacao) {
        this.dataInicioAtuacao = dataInicioAtuacao;
    }

    public Date getDataFimAtuacao() {
        return dataFimAtuacao;
    }

    public void setDataFimAtuacao(Date dataFimAtuacao) {
        this.dataFimAtuacao = dataFimAtuacao;
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
