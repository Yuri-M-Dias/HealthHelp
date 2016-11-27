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

public class ConvenioDAO extends AbstractDAO<Convenio> {

    public ConvenioDAO() {
        super(ConvenioDAO.class.getCanonicalName(), "convenio", Convenio.class);
    }

    /**
     * construtor para testes
     * @param reference referencia do database do firebase
     */
    public ConvenioDAO(DatabaseReference reference){
        super(ConvenioDAO.class.getCanonicalName(), "convenio", Convenio.class);
        setDatabaseReference(reference);
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
                        EventBus.getDefault().post(convenios);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "buscarTodos.setValueListener: onCancelled:", databaseError.toException());
                        EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
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
                    if(convenioEncontrado == null)
                        Log.w(TAG, "convenio não encontrado");
                    else {
                        convenioEncontrado.setId(dataSnapshot.getKey());
                        Log.w(TAG, "buscarPelaId.OnDataChange: o convenio encontrado foi: " +
                            convenioEncontrado.getNome());
                    }
                    EventBus.getDefault().post(convenioEncontrado);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "buscaPorNome: onCancelled:", databaseError.toException());
                    EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
                }
            });
    }

    @Override
    public void inserir(Convenio convenio) {
        DatabaseReference registroConvenio = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo convenio: " + registroConvenio.getKey());
        registroConvenio.setValue(convenio);
        convenio.setId(registroConvenio.getKey());
        registroConvenio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Convenio convenio = dataSnapshot.getValue(Convenio.class);

                // não é uma remoção
                if(convenio != null)
                    convenio.setId(dataSnapshot.getKey().toString());

                EventBus.getDefault().post(new DatabaseEvent<>("Convenio" + dataSnapshot.getKey().toString()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e(TAG, "inserir, atualizar ou remover: onCancelled:", databaseError.toException());
                EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
            }
        });
    }

}
