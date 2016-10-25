package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class LocalAtendimentoDAO extends AbstractDAO<LocalAtendimento> {

    public LocalAtendimentoDAO(DatabaseCallback callback) {
        super(LocalAtendimentoDAO.class.getCanonicalName(), "localAtendimento");
        setDatabaseCallback(callback);
    }

    /**
     * @param nomeLocal
     */
    public void buscarPorNome(String nomeLocal) {
        getDatabaseReference().child(DATABASE_CHILD).
                orderByChild("nome").equalTo(nomeLocal).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
            }
        });

    }

    /**
     * define o listener para a subchave de /localAtendimento, retornando  do firebase todos
     * os locais de atendimentos cadastrados quando este método é chamado
     */
    @Override
    public void carregarTodos() {
        setChildEventListener(new ChildEventListener() {
            List<LocalAtendimento> locaisAtendimento = new ArrayList<>();
            /**
             * retorna lista de locais cadastrados e sempre que um novo local for inserido
             *
             * @param dataSnapshot
             * @param keyIrmaoAnterior a chave do irmao anterior ao registro no banco de dados
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String keyIrmaoAnterior) {
                locaisAtendimento.add(dataSnapshot.getValue(LocalAtendimento.class));
                Log.w(TAG, "setChildListener: OnChildAdded: os locais cadastrados sao: " + locaisAtendimento);
                getDatabaseCallback().onComplete(locaisAtendimento);
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
                getDatabaseCallback().onError(databaseError);
            }

        });

        // aqui é setado como o listener para o que ocorrer em /localAtendimento/.
        getDatabaseReference().child(
                DATABASE_CHILD).addChildEventListener(getChildEventListener());
    }

    /**
     *
     * @param nomeLocal representa o nome do Local Atendimento. A key gerada pelo firebase não é utilizada
     *           pelos usuarios dos metodos publicos de LocalAtendimentoDAO
     */
    @Override
    public void carregarPelaId(int nomeLocal) {
        //TODO: criar metodo de carregar um unico local de atendimento
    }

    @Override
    public void inserir(LocalAtendimento localAtendimento) {
        getDatabaseReference().child(DATABASE_CHILD).push().setValue(
                (LocalAtendimento) localAtendimento);
    }

    /**
     * atualiza no firebase na mesma chave o novo local de atendimento.
     * Se mLocalUpdatekey for nula, o local a ser removido não foi encontrado no banco
     *
     * @param nomeLocal nome do local a ser removido do banco
     */
    @Override
    public void remover(String nomeLocal) {

        ValueEventListener buscaParaRemoverListener = new ValueEventListener() {
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

                // remove pela key
                getDatabaseReference().child(DATABASE_CHILD).child(localUpdatekey).removeValue();
            }

            /*
            operação no firebase cancelada devido a um erro
            */
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "removerLocalAtendimento: onCancelled:", databaseError.toException());
            }
        };

        //TODO: Buscar outra forma de remoção sem ser pela busca do objeto. A id deveri ser suficiente pra isso.
        //buscarPorNome(nomeLocal, buscaParaRemoverListener);
    }

    /**
     * atualiza no firebase na mesma chave o novo local de atendimento
     * Se mLocalUpdatekey for nula, o local a ser atualizado não foi encontrado no banco
     *
     * @param novoLocalAtendimento novo local com novos dados passados pelo usuario
     */
    @Override
    public void atualizar(final LocalAtendimento novoLocalAtendimento) {

        ValueEventListener buscaParaUpdateListener = new ValueEventListener() {
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

                // atualiza pela key buscada
                getDatabaseReference().child(DATABASE_CHILD).child(localUpdatekey).setValue(novoLocalAtendimento);
            }

            /*
             operação no firebase cancelada devido a um erro
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "atualizarLocalAtendimento: onCancelled:", databaseError.toException());
            }
        };

        //TODO: buscar outra forma de atualização sem ser pela busca do objeto. A id deveri ser suficiente pra isso.
        //buscarPorNome(nomeLocal, buscaParaUpdateListener);

    }

}