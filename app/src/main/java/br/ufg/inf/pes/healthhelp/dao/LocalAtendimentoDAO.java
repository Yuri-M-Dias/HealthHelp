package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

public class LocalAtendimentoDAO extends AbstractDAO<LocalAtendimento> {

    public LocalAtendimentoDAO() {
        super(LocalAtendimentoDAO.class.getCanonicalName(), "localAtendimento", LocalAtendimento.class);
    }

}
