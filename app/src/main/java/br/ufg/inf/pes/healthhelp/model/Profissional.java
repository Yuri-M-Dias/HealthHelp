package br.ufg.inf.pes.healthhelp.model;

import java.util.Date;

public class Profissional {

    private Date ultimoAcesso;
    private String profissao;


    public Date getUltimoAceso() {
        return ultimoAcesso;
    }

    public void setUltimoacesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
