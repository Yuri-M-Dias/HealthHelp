package br.ufg.inf.pes.healthhelp.dao;

/**
 * Created by gleibson on 29/09/16.
 */

public class DummyDAO {
/*
    rivate String link = "https://inner-replica-134523.firebaseio.com/";
    private DatabaseReference dbr = FirebaseDatabase.getInstance().getReference(link);
    private String colecao = "";

    public void guardarPaciente(Paciente paciente) {
        dbr.child(colecao).child(paciente.toString()).setValue(paciente);
    }

    public void atualizarPaciente(int identify, String atributo) {
        String id = Integer.toString(identify);
        dbr.child(colecao).child(id).child(atributo).setValue(atributo);
    }

    public void excluirPaciente(int identify) {
        String id = Integer.toString(identify);
        dbr.child(colecao).child(id).removeValue();
    }

    public Object buscarPaciente(int identify) {
        String id = Integer.toString(identify);
//        return  dbr.child(colecao).child(id).equals(Paciente.class);
        dbr.child(colecao).child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Paciente paciente = dataSnapshot.getValue(Paciente.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("getUser:onCancelled", databaseError.toException());
                    }
                }
        );
        return dbr;
    }

*/
}
