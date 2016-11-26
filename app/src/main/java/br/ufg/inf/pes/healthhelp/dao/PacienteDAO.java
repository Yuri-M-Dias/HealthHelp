package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import br.ufg.inf.pes.healthhelp.model.Paciente;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

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

    @Override
    public void inserir(Paciente paciente) {
        DatabaseReference registroPaciente = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo paciente: " + registroPaciente);
        registroPaciente.setValue(paciente);
        paciente.setId(registroPaciente.getKey());

        registroPaciente.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Paciente paciente = dataSnapshot.getValue(Paciente.class);

                if(paciente!=null)
                    paciente.setId(dataSnapshot.getKey().toString());
                EventBus.getDefault().post(new DatabaseEvent<>("Paciente" + dataSnapshot.getKey().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "inserir, atualizar e remover: onCancelled:", databaseError.toException());
                EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
            }
        });
    }

}
