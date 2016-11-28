package br.ufg.inf.pes.healthhelp.dao;

import com.google.firebase.database.DatabaseReference;

import br.ufg.inf.pes.healthhelp.model.Paciente;

public class PacienteDAO extends AbstractDAO<Paciente> {

    public PacienteDAO() {
        super(PacienteDAO.class.getCanonicalName(), "paciente", Paciente.class);
    }

    /**
     * construtor de testes
     * @param reference referencia do banco de dados do firebase a ser utilizado
     */
    public PacienteDAO(DatabaseReference reference){
        super(PacienteDAO.class.getCanonicalName(), "paciente", Paciente.class);
        setDatabaseReference(reference);
    }

}
