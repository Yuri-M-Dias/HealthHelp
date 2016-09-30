package br.ufg.br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.ufg.br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class LocalAtendimentoDao {

    private DatabaseReference mFirebaseDatabaseReference;

    private static final String TAG = "LocalAtendimentoDao";

    public LocalAtendimentoDao() {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child("localAtendimento").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocalAtendimento localAtendimento =  (LocalAtendimento) dataSnapshot.getValue(LocalAtendimento.class);
                Log.w(TAG, localAtendimento.getNome() + " e o endereço é: " + localAtendimento.getEndereco());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
