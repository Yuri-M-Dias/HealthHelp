package br.ufg.inf.pes.healthhelp.dao;

import br.ufg.inf.pes.healthhelp.model.Convenio;

public class ConvenioDAO extends AbstractDAO<Convenio> {

    public ConvenioDAO() {
        super(ConvenioDAO.class.getCanonicalName(), "convenio", Convenio.class);
    }

}
