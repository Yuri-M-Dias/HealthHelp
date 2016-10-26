package br.ufg.inf.pes.healthhelp.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class LocalAtendimento implements Serializable {
    @Exclude
    private String id;

    private String endereco;
    private String nome;
    private String telefone;

    private List<PeriodoTempo> horariosAtendimento;

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

    // construtor padrao para o firebase
    public LocalAtendimento() {
    }

    public LocalAtendimento(List<PeriodoTempo> horariosAtendimento, String nome, String endereco, String telefone) {
        this.horariosAtendimento = horariosAtendimento;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;

    }

    public List<PeriodoTempo> getHorariosAtendimento() {
        return horariosAtendimento;
    }

    public void setHorariosAtendimento(List<PeriodoTempo> horariosAtendimento) {
        this.horariosAtendimento = horariosAtendimento;
    }
}
