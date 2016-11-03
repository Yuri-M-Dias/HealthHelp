package br.ufg.inf.pes.healthhelp.model;

import com.google.firebase.database.Exclude;

public class Convenio {

    @Exclude
    private String id;

    private String nome;

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
}
