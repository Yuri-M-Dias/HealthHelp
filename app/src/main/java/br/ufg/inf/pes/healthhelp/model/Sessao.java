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

    public Sessao() {
        Sessao.sessao.usuario = null;
    }

    public static Sessao getInstance() {
        if (Sessao.sessao == null){
            Sessao.sessao =  new Sessao();
        }

        return Sessao.sessao;
    }

    public void criarSessao(Usuario usuario) {
        this.usuario = usuario;
    }

    public void finalizarSessao(){
        this.usuario = null;
    }

    public boolean estaAtiva(){
        return (this.usuario != null);
    }
}
