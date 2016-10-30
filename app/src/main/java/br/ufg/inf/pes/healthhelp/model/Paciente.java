package br.ufg.inf.pes.healthhelp.model;

public class Paciente extends Usuario {
    Convenio convenio;

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
}
