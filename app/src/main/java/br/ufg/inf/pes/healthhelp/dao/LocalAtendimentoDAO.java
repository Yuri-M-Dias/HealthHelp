package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

public class LocalAtendimentoDAO extends AbstractDAO<LocalAtendimento> {

    public LocalAtendimentoDAO() {
        super(LocalAtendimentoDAO.class.getCanonicalName(), "localAtendimento");
    }

    @Override
    public void buscarTodos() {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<LocalAtendimento> locaisAtendimento = new ArrayList<>();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG, "buscarTodos.addValueListener: OnDataChange: Quantidade: " + dataSnapshot.getChildrenCount());

                        for (DataSnapshot localSnapshot : dataSnapshot.getChildren()) {
                            LocalAtendimento localAtendimentoRecebido = localSnapshot.getValue(LocalAtendimento.class);
                            localAtendimentoRecebido.setId(localSnapshot.getKey());
                            locaisAtendimento.add(localAtendimentoRecebido);
                            Log.w(TAG, "Obtido: " + locaisAtendimento.get(locaisAtendimento.size() - 1).getId());
                        }

                        EventBus.getDefault().post(new DatabaseEvent<>(locaisAtendimento));
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
                    LocalAtendimento localEncontrado = dataSnapshot.getValue(LocalAtendimento.class);
                    Log.w(TAG, "buscaPorNome.OnDataChange: o local encontrado foi: " +
                        localEncontrado.getNome());
                    EventBus.getDefault().post(localEncontrado);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "buscaPorNome: onCancelled:", databaseError.toException());
                    throw databaseError.toException();
                }
            });
    }

    @Override
    public void inserir(LocalAtendimento localAtendimento) {
        DatabaseReference registroLocalAtendimento = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo local de atendimento: " + registroLocalAtendimento);
        registroLocalAtendimento.setValue(localAtendimento);
        localAtendimento.setId(registroLocalAtendimento.getKey());
        EventBus.getDefault().post(new DatabaseEvent<>("Local de atendimento salvo"));


    }

    @Override
    public void remover(LocalAtendimento localAtendimentoRemover) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(String.valueOf(localAtendimentoRemover.getId()))
            .removeValue();
    }

    @Override
    public void atualizar(LocalAtendimento novoLocalAtendimento) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(novoLocalAtendimento.getId())
            .setValue(novoLocalAtendimento);
        EventBus.getDefault().post(new DatabaseEvent<>("Local de atendimento salvo"));
    }

}
