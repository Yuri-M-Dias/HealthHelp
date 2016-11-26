package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Iterator;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

public class AgendaDAO extends AbstractDAO<Agenda> {

    public AgendaDAO() {
        super(AgendaDAO.class.getCanonicalName(), "agenda", Agenda.class);
    }

    /**
     * construtor usado para testes
     *
     * @param reference referencia do database do firebase
     */
    public AgendaDAO(DatabaseReference reference) {
        super(AgendaDAO.class.getCanonicalName(), "agenda", Agenda.class);
        setDatabaseReference(reference);
    }

    /**
     * Este método cria um evento com a primeira agenda que corresponde ao nome no firebase,
     * mesmo que mais de um seja encontrada
     *
     * @param nomeAgenda nome da agenda que se deseja obter
     */
    public void buscarPeloNome(String nomeAgenda) {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .orderByChild("nome")
            .equalTo(nomeAgenda)
            .addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG, "SNAPSHOT: " + dataSnapshot);
                        // Agenda agenda = dataSnapshot.getValue(Agenda.class);
                        GenericTypeIndicator<HashMap<String, Agenda>> t = new GenericTypeIndicator<HashMap<String, Agenda>>() {
                        };
                        HashMap<String, Agenda> agendasEncontradas = dataSnapshot.getValue(t);

                        Iterator it = agendasEncontradas.values().iterator();

                        Agenda agenda = null;
                        if (it.hasNext()) {
                            agenda = (Agenda) it.next();
                            //Salva no objeto agenda o id do banco gerado pelo firebase
                            agenda.setId(agendasEncontradas.keySet().iterator().next());
                            Log.w(TAG, "agenda buscada pelo nome: " + agenda.getId());
                        } else {
                            Log.w(TAG, "agenda não encontrada");
                        }

                        EventBus.getDefault().post(agenda);
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
    public void buscarPelaId(String id) {
        // single value, pois precisamos buscar uma unica vez, mesmo quando não há alterações na agenda
        getDatabaseReference().child(DATABASE_CHILD).child(id).
            addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.w(TAG, "SNAPSHOT: " + dataSnapshot);

                    Agenda agenda = dataSnapshot.getValue(Agenda.class);

                    //Salva no objeto agenda o id do banco gerado pelo firebase
                    if (agenda == null) {
                        Log.w(TAG, "agenda não encontrada: ");
                    } else {
                        agenda.setId(dataSnapshot.getKey());
                        Log.w(TAG, "agenda buscada pela ID: " + agenda.getId());
                    }

                    EventBus.getDefault().post(agenda);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "ValueListener: onCancelled:", databaseError.toException());
                    EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
                }
            });
    }


    @Override
    public void inserir(Agenda agenda) {
        // pega a referencia do caminho com a key gerada pelo firebase
        DatabaseReference registroAgenda = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave da nova agenda: " + registroAgenda.getKey());

        // salva no firebase
        registroAgenda.setValue(agenda);
        //Salva no objeto agenda o id do banco gerado pelo firebase
        agenda.setId(registroAgenda.getKey());

        registroAgenda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.w(TAG, "SNAPSHOT: " + dataSnapshot);
                Agenda agenda = dataSnapshot.getValue(Agenda.class);
                // agenda == null quando é uma remoção
                if (agenda != null)
                    agenda.setId(dataSnapshot.getKey().toString());
                EventBus.getDefault().post(new DatabaseEvent<>("Agenda" + dataSnapshot.getKey().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e(TAG, "ValueListener: onCancelled:", databaseError.toException());
                EventBus.getDefault().post(new DatabaseEvent<>(databaseError));
            }
        });
    }

}
