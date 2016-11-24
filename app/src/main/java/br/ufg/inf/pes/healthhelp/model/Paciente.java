package br.ufg.inf.pes.healthhelp.model;

import com.google.firebase.database.Exclude;

public class Paciente extends Usuario {

    @Exclude
    private String id;

    private Convenio convenio;

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
}
