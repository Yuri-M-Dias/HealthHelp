package br.ufg.inf.pes.healthhelp.model;

import java.util.Date;

public class Profissional {

    private Date ultimoacesso;
    private String profissao;


    public Date getUltimoAceso() {
        return ultimoacesso;
    }

    public void setUltimoacesso(Date ultimoacesso) {
        this.ultimoacesso = ultimoacesso;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
