package br.ufg.inf.pes.healthhelp.model;

public class Convenio {

    private String id;
    private String nomeConvenio;

    public String getNomeConvenio() {
        return nomeConvenio;
    }

    public void setNomeConvenio(String nome) {
        this.nomeConvenio = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
