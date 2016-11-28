package br.ufg.inf.pes.healthhelp.model;

import java.util.Date;

import br.ufg.inf.pes.healthhelp.model.enums.StatusProfissional;

public abstract class Profissional extends BaseObject {

    private Date ultimoAcesso;
    private String profissao;
    private StatusProfissional statusProfissional;
    private Usuario usuario;


    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public StatusProfissional getStatusProfissional() {
        return statusProfissional;
    }

    public void setStatusProfissional(StatusProfissional statusProfissional) {
        this.statusProfissional = statusProfissional;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
