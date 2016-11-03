package br.ufg.inf.pes.healthhelp.model;

public class Sessao {
    private static Sessao sessao;
    private  Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sessao() {}

    public static Sessao getInstance() {
        return Sessao.sessao;
    }

    public void criarSessao(Usuario usuario) {}

    public void finalizarSessao(){}

    public boolean estaAtiva(){
        //TODO: implementação?
        return true;
    }
}
