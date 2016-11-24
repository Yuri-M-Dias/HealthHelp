package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.Atuacao;

/**
 * Esta classe é responsável por operações de banco de dados relacionadas a uma {@link Atuacao}.
 */
public class AtuacaoDAO extends AbstractDAO<Atuacao> {

    public AtuacaoDAO(){
        super(AtuacaoDAO.class.getCanonicalName(), "atuacao", Atuacao.class);
    }

}
