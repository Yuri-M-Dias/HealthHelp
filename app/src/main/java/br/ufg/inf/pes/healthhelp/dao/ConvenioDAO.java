package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Convenio;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

/**
 * Created by gleibson on 24/11/16.
 */

public class ConvenioDAO extends AbstractDAO<Convenio> {

    public ConvenioDAO(String TAG, String DATABASE_CHILD) {
        super(TAG, DATABASE_CHILD);
    }

    @Override
    public void buscarTodos() {
        getDatabaseReference().child(DATABASE_CHILD).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<Convenio> convenios = new ArrayList<>();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG, "buscarTodos.addValueListener: OnDataChange: Quantidade: " +
                            dataSnapshot.getChildrenCount());
                        for (DataSnapshot localSnapshot : dataSnapshot.getChildren()) {
                            Convenio convenioRecebido = localSnapshot.getValue(Convenio.class);
                            convenioRecebido.setId(localSnapshot.getKey());
                            convenios.add(convenioRecebido);
                            Log.w(TAG, "Obtido: " + convenios.get(convenios.size() - 1).getId());
                        }
                        EventBus.getDefault().post(new DatabaseEvent<>(convenios));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "buscarTodos.setValueListener: onCancelled:", databaseError.toException());
                        throw databaseError.toException();
                    }
                }
            );
    }

    @Override
    public void buscarPelaId(String id) {
        getDatabaseReference()
            .child(DATABASE_CHILD).child(id)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Convenio convenioEncontrado = dataSnapshot.getValue(Convenio.class);
                    Log.w(TAG, "buscaPorNome.OnDataChange: o convenio encontrado foi: " +
                        convenioEncontrado.getNome());
                    EventBus.getDefault().post(convenioEncontrado);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "buscaPorNome: onCancelled:", databaseError.toException());
                    throw databaseError.toException();
                }
            });
    }

    @Override
    public void inserir(Convenio convenio) {
        DatabaseReference registroConvenio = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo convenio: " + registroConvenio);
        registroConvenio.setValue(convenio);
        convenio.setId(registroConvenio.getKey());
        EventBus.getDefault().post(new DatabaseEvent<>("Convenio salvo"));
    }

    @Override
    public void remover(Convenio convenioRemover) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(String.valueOf(convenioRemover.getId()))
            .removeValue();
    }

    @Override
    public void atualizar(Convenio novoConvenio) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(novoConvenio.getId())
            .setValue(novoConvenio);
        EventBus.getDefault().post(new DatabaseEvent<>("Convenio salvo"));
    }
}
