package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

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
        getDatabaseReference().child(DATABASE_CHILD).addListenerForSingleValueEvent(criaValueListenerDeBusca());

    }

    @Override
    public void buscarPelaId(String id) {
        // single value, pois precisamos buscar uma unica vez, mesmo quando não há alterações na agenda
        getDatabaseReference().child(DATABASE_CHILD).child(id).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                                Agenda agenda = dataSnapshot.getValue(Agenda.class);

                                //Salva no objeto agenda o id do banco gerado pelo firebase
                                agenda.setId(dataSnapshot.getKey());
                                Log.w(TAG,"agenda buscada pela ID: " + agenda.getId());
                                EventBus.getDefault().post(new DatabaseEvent<>(agenda));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "ValueListener: onCancelled:", databaseError.toException());
                        EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
                    }
                });

    }

    public void buscarPeloNome(String nomeAgenda) {
        getDatabaseReference().child(DATABASE_CHILD).
                orderByChild("nome").equalTo(nomeAgenda).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Agenda agenda = dataSnapshot.getValue(Agenda.class);

                        //Salva no objeto agenda o id do banco gerado pelo firebase
                        agenda.setId(dataSnapshot.getKey());
                        Log.w(TAG,"agenda buscada pelo nome: " + agenda.getId());
                        EventBus.getDefault().post(new DatabaseEvent<>(agenda));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Log.e(TAG, "ValueListener: onCancelled:", databaseError.toException());
                        EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
                    }
                }
        );
    }

    @Override
    public void inserir(Agenda agenda) {
        // pega a referencia do caminho com a key gerada pelo firebase
        DatabaseReference registroAgenda = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave da nova agenda: " + registroAgenda.getKey());

        registroAgenda.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.w(TAG, "SNAPSHOT: " + dataSnapshot);
                Agenda agenda = dataSnapshot.getValue(Agenda.class);
                //Salva no objeto agenda o id do banco gerado pelo firebase
                agenda.setId(dataSnapshot.getKey().toString());
                Log.w(TAG, "agenda adicionada: " + agenda.getId());
                EventBus.getDefault().post(new DatabaseEvent<>("Agenda adicionada " + agenda.getNome()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e(TAG, "ValueListener: onCancelled:", databaseError.toException());
                EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
            }
        });
        // salva no firebase
        registroAgenda.setValue(agenda);
        agenda.setId(registroAgenda.getKey());

    }

    @Override
    public void remover(Agenda agendaRemover) {

        getDatabaseReference().child(DATABASE_CHILD).child( agendaRemover.getId()  ).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Agenda agenda = dataSnapshot.getValue(Agenda.class);

                        //Salva no objeto agenda o id do banco gerado pelo firebase
                        agenda.setId(dataSnapshot.getKey());
                        Log.w(TAG,"agenda removida: " + agenda.getId());
                        EventBus.getDefault().post(new DatabaseEvent<>("Agenda removida" + agenda.getNome()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "ValueListener: onCancelled:", databaseError.toException());
                        EventBus.getDefault().post(new DatabaseEvent<>(databaseError));

                    }
                }
        );
        // remove pela key que corresponde a id da agenda
        getDatabaseReference().child(DATABASE_CHILD).child( agendaRemover.getId()  ).removeValue();

    }

    @Override
    public void atualizar(Agenda novaAgenda) {

        getDatabaseReference().child(DATABASE_CHILD).child(novaAgenda.getId()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Agenda agenda = dataSnapshot.getValue(Agenda.class);

                        //Salva no objeto agenda o id do banco gerado pelo firebase
                        agenda.setId(dataSnapshot.getKey());
                        Log.w(TAG,"agenda atualizada: " + agenda.getId());
                        EventBus.getDefault().post(new DatabaseEvent<>("Agenda atualizada" + agenda.getNome()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "ValueListener: onCancelled:", databaseError.toException());
                        EventBus.getDefault().post(new DatabaseEvent<>(databaseError));

                    }
                }
        );
        // atualiza pela id da agenda que corresponde a key no firebase
        getDatabaseReference().child(DATABASE_CHILD).child(novaAgenda.getId()).setValue(novaAgenda);

    }

    private ValueEventListener criaValueListenerDeBusca() {
        return new ValueEventListener() {
            List<Agenda> agendas = new ArrayList<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot agendaSnapshot : dataSnapshot.getChildren()) {
                        Agenda agenda = agendaSnapshot.getValue(Agenda.class);
                        agenda.setId(agendaSnapshot.getKey());
                        agendas.add(agenda);
                        Log.w(TAG, "Obtido: " + agendas.get(agendas.size() - 1).getId());
                    }


                    EventBus.getDefault().post(new DatabaseEvent<>(agendas));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "buscar.valueListenerLista: onCancelled:", databaseError.toException());
                EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
            }


        };

    }
}
