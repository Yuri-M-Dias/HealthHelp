package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.BaseObject;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

public abstract class AbstractDAO<T extends BaseObject> implements InterfaceDAO<T> {

    protected final String TAG;
    protected final String DATABASE_CHILD;

    protected DatabaseReference databaseReference;

    protected final Class<T> tipoEntidade;

    public AbstractDAO(String TAG, String DATABASE_CHILD, Class<T> tipoEntidade) {
        this.TAG = TAG;
        this.DATABASE_CHILD = DATABASE_CHILD;
        this.tipoEntidade = tipoEntidade;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    protected DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    protected void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public Class<T> getTipoEntidade() {
        return tipoEntidade;
    }

    @Override
    public void buscarTodos() {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<T> objetosEncontrados = new ArrayList<>();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG, DATABASE_CHILD + " buscarTodos com sucesso. Quantidade: " + dataSnapshot.getChildrenCount());

                        for (DataSnapshot localSnapshot : dataSnapshot.getChildren()) {
                            T objetoEncontrado = localSnapshot.getValue(getTipoEntidade());
                            objetoEncontrado.setId(localSnapshot.getKey());
                            objetosEncontrados.add(objetoEncontrado);
                            Log.w(TAG, "Obtido " + DATABASE_CHILD + ": " + objetoEncontrado.getId());
                        }
                        EventBus.getDefault().post(new DatabaseEvent<>(objetosEncontrados));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, DATABASE_CHILD + " buscarTodos com falha.");
                        throw databaseError.toException();
                    }
                }
            );
    }

    @Override
    public void buscarPelaId(String id) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(id)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    T objetoEncontrado = dataSnapshot.getValue(getTipoEntidade());
                    Log.w(TAG, DATABASE_CHILD + " buscaPelaId com sucesso: " + objetoEncontrado.getId());
                    EventBus.getDefault().post(objetoEncontrado);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, DATABASE_CHILD + "buscaPelaId com sucesso: ", databaseError.toException());
                    throw databaseError.toException();
                }
            });
    }

    @Override
    public void inserir(T objeto) {
        DatabaseReference registro = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo " + DATABASE_CHILD + ": " + registro.getKey());
        registro.setValue(objeto);
        objeto.setId(registro.getKey());
    }

    @Override
    public void remover(T objeto) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(String.valueOf(objeto.getId()))
            .removeValue();
    }

    @Override
    public void atualizar(T objeto) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(objeto.getId())
            .setValue(objeto);
    }

}
