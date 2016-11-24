package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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

                    for (DataSnapshot pacienteSnapshot: dataSnapshot.getChildren()) {
                        Paciente pacienteRecebido = pacienteSnapshot.getValue(Paciente.class);
                        pacienteRecebido.setId(pacienteSnapshot.getKey());
                        pacientes.add(pacienteRecebido);
                        Log.w(TAG,"Obtido: " + pacientes.get(pacientes.size()-1).getId());
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

    }

    @Override
    public void inserir(Paciente objeto) {

    }

    @Override
    public void remover(Paciente objeto) {

    }

    @Override
    public void atualizar(Paciente objeto) {

    }


}
