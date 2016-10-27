package br.ufg.inf.pes.healthhelp.model;

public class Convenio extends Paciente{

    String nomeconvenio;

    public String getNomeConvenio(){
        return nomeconvenio;
    }

    public void setNomeConvenio(String nome){
        this.nomeconvenio = nome;
    }
}
