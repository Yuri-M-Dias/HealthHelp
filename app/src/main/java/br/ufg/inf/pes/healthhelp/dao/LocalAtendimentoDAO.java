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
    public void carregarTodos() {
        // aqui é setado como o listener para o que ocorrer em /localAtendimento/.
        getDatabaseReference().child(DATABASE_CHILD).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<LocalAtendimento> locaisAtendimento = new ArrayList<>();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG,"carregarTodos.addValueListener: OnDataChange: Quantidade: "+dataSnapshot.getChildrenCount());

                        for (DataSnapshot localSnapshot: dataSnapshot.getChildren()) {
                            LocalAtendimento local = localSnapshot.getValue(LocalAtendimento.class);
                            locaisAtendimento.add(dataSnapshot.getValue(LocalAtendimento.class));
                            Log.w(TAG,"carregarTodos.addValueListener: OnDataChange: Obtido:" + local.getNome());
                        }

                        Log.w(TAG, "carregarTodos.addValueListener: OnDataChange: Os locais cadastrados sao: " + locaisAtendimento);
                        getDatabaseCallback().onComplete(locaisAtendimento);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "carregarTodos.setValueListener: onCancelled:", databaseError.toException());
                        getDatabaseCallback().onError(databaseError);
                    }
                }
        );


    }

    /**
     * @param nomeLocal representa o nome do Local Atendimento. A key gerada pelo firebase não é utilizada
     *                  pelos usuarios dos metodos publicos de LocalAtendimentoDAO
     */
    @Override
    public void carregarPelaId(int nomeLocal) {
        //TODO: criar metodo de carregar um unico local de atendimento
    }

    @Override
    public void inserir(LocalAtendimento localAtendimento) {
        getDatabaseReference().child(DATABASE_CHILD).child(
                String.valueOf( localAtendimento.getId() ) ).push().setValue(
                (LocalAtendimento) localAtendimento);
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
    public void atualizar(final LocalAtendimento novoLocalAtendimento) {

        // atualiza pela id do local que corresponde a key no firebase
        getDatabaseReference().child(DATABASE_CHILD).child(
                String.valueOf( novoLocalAtendimento.getId() ) ).setValue(novoLocalAtendimento);

    }

}