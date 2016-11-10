package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;

public class AgendaDAO extends AbstractDAO<Agenda> {
    public AgendaDAO() {
        super(AgendaDAO.class.getCanonicalName(), "agenda");
        setDatabaseReference(FirebaseDatabase.getInstance().getReference());
    }

    /**
     * construtor usado para testes
     * @param reference referencia do database do firebase
     */
    public AgendaDAO(DatabaseReference reference) {
        super(AgendaDAO.class.getCanonicalName(), "agenda");
        setDatabaseReference(reference);
    }

    @Override
    public void buscarTodos() {
        // aqui é setado como o listener para o que ocorrer em /agenda/.
        getDatabaseReference().child(DATABASE_CHILD).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<Agenda> agendas = new ArrayList<>();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG,"buscarTodos.addValueListener: OnDataChange: Quantidade: "+dataSnapshot.getChildrenCount());

                        for (DataSnapshot agendaSnapshot: dataSnapshot.getChildren()) {
                            Agenda agenda = agendaSnapshot.getValue(Agenda.class);
                            agenda.setId(agendaSnapshot.getKey());
                            agendas.add(agenda);
                            Log.w(TAG,"Obtido: " + agendas.get(agendas.size()-1).getId());
                        }

                        getDatabaseCallback().onComplete(agendas);
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
        // single value, pois precisamos buscar uma unica vez, mesmo quando não há alterações na agenda
        getDatabaseReference().child(DATABASE_CHILD).child(id).
                addListenerForSingleValueEvent(criaValueEventListener());
    }

    public void buscarPeloNome(String nomeAgenda) {
        getDatabaseReference().child(DATABASE_CHILD).
                orderByChild("nome").equalTo(nomeAgenda).addListenerForSingleValueEvent(criaValueEventListener());
    }

    @Override
    public void inserir(Agenda agenda) {
        // pega a referencia do caminho com a key gerada pelo firebase
        DatabaseReference registroAgenda = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave da nova agenda: " + registroAgenda.getKey());
        agenda.setId(registroAgenda.getKey());

        registroAgenda.addListenerForSingleValueEvent(criaValueEventListener());

        // salva no firebase
        registroAgenda.setValue(agenda);
    }

    @Override
    public void remover(Agenda agendaRemover) {

        getDatabaseReference().child(DATABASE_CHILD).child(agendaRemover.getId()).
                addListenerForSingleValueEvent(criaValueEventListener());

        // remove pela key que corresponde a id da agenda
        getDatabaseReference().child(DATABASE_CHILD).child( agendaRemover.getId()  ).removeValue();

    }

    @Override
    public void atualizar(Agenda novaAgenda) {

        getDatabaseReference().child(DATABASE_CHILD).child(novaAgenda.getId()).
                addListenerForSingleValueEvent( criaValueEventListener() );

        // atualiza pela id da agenda que corresponde a key no firebase
        getDatabaseReference().child(DATABASE_CHILD).child(novaAgenda.getId()).setValue(novaAgenda);

    }

    private ValueEventListener criaValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Agenda agenda = dataSnapshot.getValue(Agenda.class);

                //Salva no objeto agenda o id do banco gerado pelo firebase
                agenda.setId(dataSnapshot.getKey());
                Log.w(TAG,"addValueListener Obtido: " + agenda.getId());
                getDatabaseCallback().onComplete(agenda);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "addValueListener: onCancelled:", databaseError.toException());
                getDatabaseCallback().onError(databaseError);
            }
        };
    }
}
