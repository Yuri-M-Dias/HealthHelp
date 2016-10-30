package br.ufg.inf.pes.healthhelp.model;

import java.util.Date;
import java.util.List;

public class Usuario {

    private String login;
    private String senha;
    private String nome;
    private List<String> telefone;
    private List<String> email;
    private List<String> telefones;
    private char sexo;
    private Date dataNascimento;
    private List<Profissional> ocupacoes;

    public List<Profissional> getOcupacoes() {
        return ocupacoes;
    }

    public void setOcupacoes(List<Profissional> ocupacoes) {
        this.ocupacoes = ocupacoes;
    }

    public List<String> getTelefones() { return telefones; }

    public void setTelefones(List<String> telefones) { this.telefones = telefones; }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getTelefone() {
        return telefone;
    }

    public void setTelefone(List<String> telefone) {
        this.telefone = telefone;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
