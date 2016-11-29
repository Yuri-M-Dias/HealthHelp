package br.ufg.inf.pes.healthhelp.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.excecao.ObjetoInexistenteException;
import br.ufg.inf.pes.healthhelp.model.BaseObject;

public abstract class AbstractDAO<T extends BaseObject> implements InterfaceDAO<T> {

    protected final String TAG;
    protected final String DATABASE_CHILD;
    protected final Class<T> tipoEntidade;
    protected DatabaseReference databaseReference;

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
            .addListenerForSingleValueEvent(getValueListenerGenericoLista("buscar todos"));
    }

    @Override
    public void buscarPelaId(final String id) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(id)
            .addListenerForSingleValueEvent(getValueListenerGenerico("buscar pela id"));
    }

    @Override
    public void inserir(T objeto) {
        DatabaseReference registro = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo " + DATABASE_CHILD + ": " + registro.getKey());
        registro.setValue(objeto);
        objeto.setId(registro.getKey());
        registro.addListenerForSingleValueEvent(getValueListenerGenerico("inserir"));
    }

    @Override
    public void remover(T objeto) {
        final String idObjeto = String.valueOf(objeto.getId());
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(idObjeto)
            .removeValue()
            .addOnCompleteListener(getOnCompleteListenerGenerico(idObjeto));
    }

    @Override
    public void atualizar(T objeto) {
        //Devia postar o objeto atualizado
        final String idObjeto = objeto.getId();
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .child(idObjeto)
            .setValue(objeto)
            .addOnCompleteListener(getOnCompleteListenerGenerico(idObjeto));
    }

    protected ValueEventListener getValueListenerGenerico(final String nomeMetodo) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    String mensagemErro = DATABASE_CHILD + " " + nomeMetodo + " com falha";
                    Log.w(TAG, mensagemErro);
                    throw new ObjetoInexistenteException(mensagemErro);
                }
                T objetoEncontrado = construirObjetoFirebase(dataSnapshot);
                Log.w(TAG, DATABASE_CHILD + " " + nomeMetodo + " com sucesso: " + objetoEncontrado.getId());
                EventBus.getDefault().post(objetoEncontrado);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, DATABASE_CHILD + "buscaPelaId com falha: ", databaseError.toException());
                throw databaseError.toException();
            }
        };
    }

    protected ValueEventListener getValueListenerGenericoLista(final String nomeMetodo) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    String mensagemErro = DATABASE_CHILD + " " + nomeMetodo + " com falha";
                    Log.w(TAG, mensagemErro);
                    throw new ObjetoInexistenteException(mensagemErro);
                }
                long quantidadeChildren = dataSnapshot.getChildrenCount();
                Log.w(TAG, "Obtida lista de " + DATABASE_CHILD + ", com tamanho: " + quantidadeChildren);
                List<T> objetosEncontrados = new ArrayList<>();
                for (DataSnapshot localSnapshot : dataSnapshot.getChildren()) {
                    T objetoEncontrado = construirObjetoFirebase(localSnapshot);
                    objetosEncontrados.add(objetoEncontrado);
                    Log.w(TAG, "Obtido " + DATABASE_CHILD + ": " + objetoEncontrado.getId());
                }
                EventBus.getDefault().post(objetosEncontrados);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, DATABASE_CHILD + "buscaPelaId com falha: ", databaseError.toException());
                throw databaseError.toException();
            }
        };
    }

    protected OnCompleteListener<Void> getOnCompleteListenerGenerico(final String idObjeto) {
        return new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.w(TAG, DATABASE_CHILD + " removido com sucesso: " + idObjeto);
                } else {
                    Log.w(TAG, DATABASE_CHILD + " removido com falha: " + idObjeto);
                }
                //Necessário informar sucesso ou falha...
                EventBus.getDefault().post(idObjeto);
            }
        };
    }

    protected T construirObjetoFirebase(DataSnapshot snapshot) {
        T objetoEncontrado = null;
        GenericTypeIndicator tipoParaConverter = getTipoEntidadeConstrucao();
        if (tipoParaConverter != null) {
            objetoEncontrado = (T) snapshot.getValue(tipoParaConverter);
        } else {
            objetoEncontrado = snapshot.getValue(getTipoEntidade());
        }
        objetoEncontrado.setId(snapshot.getKey());
        return objetoEncontrado;
    }

    protected GenericTypeIndicator<T> getTipoEntidadeConstrucao() {
        return null;
    }

}
