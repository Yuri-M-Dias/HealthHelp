package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.service.LocalAtendimentoService;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class LocalAtendimentoDAO {

    private static final String TAG = "LocalAtendimentoDao";
    private static final String HEALTHHELP_CHILD = "localAtendimento";

    // local atendimento fornecido a ser atualizado
    private LocalAtendimento mLocalUpdate;

    private DatabaseReference mFirebaseDatabaseReference;
    private List<LocalAtendimento> mLocaisAtendimento;
    private LocalAtendimentoService mLocalAtendimentoService;

    private ChildEventListener firebaseChildEventListener;

    public LocalAtendimentoDAO(LocalAtendimentoService localAtendimentoService) {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mLocalAtendimentoService = localAtendimentoService;
        mLocaisAtendimento = new ArrayList<>();
        setChildListener();
    }

    public List<LocalAtendimento> getmLocaisAtendimento() {
        return mLocaisAtendimento;
    }

    public void setmLocaisAtendimento(List<LocalAtendimento> mLocaisAtendimento) {
        this.mLocaisAtendimento = mLocaisAtendimento;
    }

    public void criarLocalAtendimento(LocalAtendimento localAtendimento) {
        mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).push().setValue(localAtendimento);
    }

    /**
     * @param nomeLocal
     * @param resultListener resultado da busca é retornado no result listener passado pelo método invocador
     */
    public void buscarPorNomeLocalAtendimento(String nomeLocal, ValueEventListener resultListener) {
        mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).
                orderByChild("nome").equalTo(nomeLocal).addListenerForSingleValueEvent(resultListener);

    }

    /**
     * atualiza no firebase na mesma chave o novo local de atendimento
     * Se mLocalUpdatekey for nula, o local a ser atualizado não foi encontrado no banco
     *
     * @param nomeLocal            o nome do local a ser atualizado. Sendo o nome ques esta cadastrado no banco
     *                             antes do update
     * @param novoLocalAtendimento novo local com novos dados passados pelo usuario
     */
    public void atualizarLocalAtendimento(String nomeLocal, LocalAtendimento novoLocalAtendimento) {
        mLocalUpdate = novoLocalAtendimento;

        ValueEventListener updateLocalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // firebase requer que seja um GenericTypeIndicator.
                // https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/GenericTypeIndicator.html
                GenericTypeIndicator<HashMap<String, LocalAtendimento>> t =
                        new GenericTypeIndicator<HashMap<String, LocalAtendimento>>() {
                        };
                HashMap<String, LocalAtendimento> locAt = dataSnapshot.getValue(t);
                Log.w(TAG, "atualizarLocalAtendimento: OnDataChange: o local encontrado foi: " +
                        locAt.values().iterator().next().getNome());

                // salva a firebase key do local buscado
                String localUpdatekey = locAt.keySet().iterator().next();

                mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).child(localUpdatekey).setValue(mLocalUpdate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "atualizarLocalAtendimento: onCancelled:", databaseError.toException());
            }
        };

        buscarPorNomeLocalAtendimento(nomeLocal, updateLocalListener);

    }

    /**
     * atualiza no firebase na mesma chave o novo local de atendimento.
     * Se mLocalUpdatekey for nula, o local a ser removido não foi encontrado no banco
     *
     * @param nomeLocal nome do local a ser removido do banco
     */
    public void removerLocalAtendimento(String nomeLocal) {

        ValueEventListener removeLocalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // firebase requer que seja um GenericTypeIndicator.
                // https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/GenericTypeIndicator.html
                GenericTypeIndicator<HashMap<String, LocalAtendimento>> t =
                        new GenericTypeIndicator<HashMap<String, LocalAtendimento>>() {
                        };
                HashMap<String, LocalAtendimento> locAt = dataSnapshot.getValue(t);
                Log.w(TAG, "removerLocalAtendimento: OnDataChange: o local encontrado foi: " +
                        locAt.values().iterator().next().getNome());

                // salva a firebase key do local buscado
                String localUpdatekey = locAt.keySet().iterator().next();

                mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).child(localUpdatekey).removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "removerLocalAtendimento: onCancelled:", databaseError.toException());
            }
        };

        buscarPorNomeLocalAtendimento(nomeLocal, removeLocalListener);

    }

    /**
     * define o listener para a subchave de /localAtendimento, retornando  do firebase todos
     * os locais de atendimentos cadastrados quando este método é chamado
     */
    public void setChildListener() {
        firebaseChildEventListener = new ChildEventListener() {
            /**
             * retorna lista de locais cadastrados e sempre que um novo local for inserido
             *
             * @param dataSnapshot
             * @param keyIrmaoAnterior a chave do irmao anterior ao registro no banco de dados
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String keyIrmaoAnterior) {
                mLocaisAtendimento.add(dataSnapshot.getValue(LocalAtendimento.class));
                Log.w(TAG, "setChildListener: OnChildAdded: os locais cadastrados sao: " + mLocaisAtendimento);
                mLocalAtendimentoService.receberLocaisAtendimento(mLocaisAtendimento);
            }

            /**
             * chamado depois do update de um local realizado no banco
             *
             * @param dataSnapshot
             * @param keyIrmaoAnterior a chave do irmao anterior ao registro no banco de dados
             */
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String keyIrmaoAnterior) {
                //TODO: mandar sinal de confirmacao para o usuario

            }

            /**
             *   chamado depois do delete de um local realizado no banco
             *
             * @param dataSnapshot
             */
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //TODO: mandar sinal de confirmacao para o usuario
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String keyIrmaoAnterior) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "setChildListener: onCancelled:", databaseError.toException());
                mLocalAtendimentoService.receberLocaisAtendimento(null);
            }

        };

        // aqui é setado como o listener para o que ocorrer em /localAtendimento/.
        mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).addChildEventListener(firebaseChildEventListener);

    }
}