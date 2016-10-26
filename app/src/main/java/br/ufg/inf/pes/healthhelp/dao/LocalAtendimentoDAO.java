package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    public LocalAtendimentoDAO() {
        super(LocalAtendimentoDAO.class.getCanonicalName(), "localAtendimento");
    }

    /**
     * @param nomeLocal nome do local atendimento salvo no banco de dados
     */
    public void buscarPorNome(String nomeLocal) {
        getDatabaseReference().child(DATABASE_CHILD).
                orderByChild("nome").equalTo(nomeLocal).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // firebase requer que seja um GenericTypeIndicator.
                // https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/GenericTypeIndicator.html
                GenericTypeIndicator<HashMap<String, LocalAtendimento>> t =
                        new GenericTypeIndicator<HashMap<String, LocalAtendimento>>() {
                        };

                HashMap<String, LocalAtendimento> locAt = dataSnapshot.getValue(t);

                LocalAtendimento localEncontrado = locAt.values().iterator().next();
                Log.w(TAG, "buscaPorNome.OnDataChange: o local encontrado foi: " +
                                localEncontrado.getNome());

                // chama de volta quem pediu o local que foi buscado
                getDatabaseCallback().onComplete(localEncontrado);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "buscaPorNome: onCancelled:", databaseError.toException());
                getDatabaseCallback().onError(databaseError);
            }
        });

    }

    /**
     * define o listener para a subchave de /localAtendimento. Retorna, no momento em que o listener
     *  é adicionado, todos os locais de atendimentos cadastrados.
     */
    @Override
    public void buscarTodos() {
        // aqui é setado como o listener para o que ocorrer em /localAtendimento/.
        getDatabaseReference().child(DATABASE_CHILD).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<LocalAtendimento> locaisAtendimento = new ArrayList<>();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG,"buscarTodos.addValueListener: OnDataChange: Quantidade: "+dataSnapshot.getChildrenCount());

                        for (DataSnapshot localSnapshot: dataSnapshot.getChildren()) {
                            LocalAtendimento localAtendimentoRecebido = localSnapshot.getValue(LocalAtendimento.class);
                            localAtendimentoRecebido.setId(localSnapshot.getKey());
                            locaisAtendimento.add(localAtendimentoRecebido);
                            Log.w(TAG,"Obtido: " + locaisAtendimento.get(locaisAtendimento.size()-1).getId());
                        }

                        getDatabaseCallback().onComplete(locaisAtendimento);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "buscarTodos.setValueListener: onCancelled:", databaseError.toException());
                        getDatabaseCallback().onError(databaseError);
                    }
                }
        );


    }

    @Override
    public void buscarPelaId(String id) {
        //TODO: criar metodo de carregar um unico local de atendimento
    }

    @Override
    public void inserir(LocalAtendimento localAtendimento) {
        DatabaseReference registroLocalAtendimento = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo local de atendimento: " + registroLocalAtendimento.getKey());
        registroLocalAtendimento.setValue(localAtendimento);
        localAtendimento.setId(registroLocalAtendimento.getKey());
    }

    /**
     * remove no firebase pela chave de um local de atendimento
     * Se mLocalUpdatekey for nula, o local a ser removido não foi encontrado no banco
     *
     * @param localAtendimentoRemover nome do local a ser removido do banco
     */
    @Override
    public void remover(LocalAtendimento localAtendimentoRemover) {

        // remove pela key que corresponde ao id do local
        getDatabaseReference().child(DATABASE_CHILD).child(
                String.valueOf( localAtendimentoRemover.getId() ) ).removeValue();

    }

    /**
     * atualiza no firebase na mesma chave o novo local de atendimento
     *
     * @param novoLocalAtendimento novo local com novos dados passados pelo usuario
     */
    @Override
    public void atualizar(LocalAtendimento novoLocalAtendimento) {

        // atualiza pela id do local que corresponde a key no firebase
        getDatabaseReference().child(DATABASE_CHILD).child(novoLocalAtendimento.getId()).setValue(novoLocalAtendimento);

    }

}