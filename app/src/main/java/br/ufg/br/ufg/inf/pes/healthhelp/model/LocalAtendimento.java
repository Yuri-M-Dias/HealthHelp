package br.ufg.br.ufg.inf.pes.healthhelp.model;

import java.util.List;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class LocalAtendimento {
    private String nome;
    private String endereco;
    private String telefone;

    private List<PeriodoTempo> horariosAtendimento;

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
