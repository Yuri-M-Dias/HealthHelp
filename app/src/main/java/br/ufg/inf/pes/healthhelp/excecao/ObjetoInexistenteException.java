package br.ufg.inf.pes.healthhelp.excecao;

/**
 * Exceção lançada quando uma chamada é feita no Firebase para um objeto que não existe
 */
public class ObjetoInexistenteException extends RuntimeException {

    public ObjetoInexistenteException() {
        super();
    }

    public ObjetoInexistenteException(String mensagemErro) {
        super(mensagemErro);
    }

}
