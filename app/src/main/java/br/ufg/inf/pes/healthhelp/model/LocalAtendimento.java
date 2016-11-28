package br.ufg.inf.pes.healthhelp.model;

import java.util.List;

public class LocalAtendimento extends BaseObject {

    private String endereco;
    private String nome;
    private String telefone;

    private List<PeriodoTempo> horariosAtendimento;

    // construtor padrao para o firebase
    public LocalAtendimento() {
    }

    public LocalAtendimento(List<PeriodoTempo> horariosAtendimento, String nome, String endereco, String telefone) {
        this.horariosAtendimento = horariosAtendimento;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<PeriodoTempo> getHorariosAtendimento() {
        return horariosAtendimento;
    }

    public void setHorariosAtendimento(List<PeriodoTempo> horariosAtendimento) {
        this.horariosAtendimento = horariosAtendimento;
    }
}
