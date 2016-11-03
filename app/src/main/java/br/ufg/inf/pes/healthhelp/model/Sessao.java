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

    private Sessao() {
        sessao.usuario = null;
    }

    public static Sessao getInstance() {
        if (sessao == null){
            sessao =  new Sessao();
        }

        return sessao;
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
