package br.ufg.inf.pes.healthhelp.model;

public class Paciente extends Usuario {
    private Convenio convenio;
    private String id;

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
