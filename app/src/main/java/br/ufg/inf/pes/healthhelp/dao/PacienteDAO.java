package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Paciente;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

/**
 * Created by gleibson on 24/11/16.
 */

public class PacienteDAO extends AbstractDAO<Paciente> {

    public PacienteDAO(String TAG, String DATABASE_CHILD) {
        super(TAG, DATABASE_CHILD);
    }

    @Override
    public void buscarTodos() {
        getDatabaseReference().child(DATABASE_CHILD).addListenerForSingleValueEvent(
            new ValueEventListener() {
                List<Paciente> pacientes = new ArrayList<Paciente>();

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.w(TAG, "buscarTodos.addValueListener: OnDataChange: Quantidade: " +
                        dataSnapshot.getChildrenCount());

                    for (DataSnapshot pacienteSnapshot : dataSnapshot.getChildren()) {
                        Paciente pacienteRecebido = pacienteSnapshot.getValue(Paciente.class);
                        pacienteRecebido.setId(pacienteSnapshot.getKey());
                        pacientes.add(pacienteRecebido);
                        Log.w(TAG, "Obtido: " + pacientes.get(pacientes.size() - 1).getId());
                    }
                    EventBus.getDefault().post(new DatabaseEvent<>(pacientes));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "buscarTodos.setValueListener: onCancelled:", databaseError.
                        toException());
                    throw databaseError.toException();
                }
            });
    }

    @Override
    public void buscarPelaId(String id) {
        getDatabaseReference().child(DATABASE_CHILD).orderByChild("id").equalTo(id).
            addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     Paciente pacienteEncontrado = dataSnapshot.getValue(Paciente.class);
                     Log.w(TAG, "buscaPeloId.OnDataChange: o paciente encontrado foi: " +
                             pacienteEncontrado.getNome());
                     EventBus.getDefault().post(pacienteEncontrado);
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {
                     Log.e(TAG, "buscaPelaId: onCancelled:", databaseError.toException());
                     throw databaseError.toException();
                 }
            }
        );
    }

    @Override
    public void inserir(Paciente paciente) {
        DatabaseReference registroPaciente = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo paciente: " + registroPaciente);
        registroPaciente.setValue(paciente);
        paciente.setId(registroPaciente.getKey());
        EventBus.getDefault().post(new DatabaseEvent<>("Paciente salvo"));
    }

    @Override
    public void remover(Paciente pacienteRemover) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(String.valueOf(pacienteRemover.getId()))
            .removeValue();
    }

    @Override
    public void atualizar(Paciente novoPaciente) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(novoPaciente.getId())
            .setValue(novoPaciente);
        EventBus.getDefault().post(new DatabaseEvent<>("Paciente salvo"));
    }


}
