package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class LocalAtendimentoDAO {

    private DatabaseReference mFirebaseDatabaseReference;

    private static final String TAG = "LocalAtendimentoDao";
    private static final String HEALTHHELP_CHILD = "localAtendimento";


    public LocalAtendimentoDAO() {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


        // mock do primeiro registro de horariosAtendimento de um local
        ArrayList<PeriodoTempo> horarios = new ArrayList<>();
        Calendar cal = new GregorianCalendar();

        cal.set(Calendar.YEAR, 2016);
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DAY_OF_YEAR, 26);

        horarios.add(
                new PeriodoTempo(
                        "8",
                        "22",
                        cal.getTime(),
                        cal.getTime(),
                        "23456"
                )
        );
        //inserindo mock de primeiro localAtendimento
        //criarLocalAtendimento(mFirebaseDatabaseReference, new LocalAtendimento("Hospital Santa Cecília", "Avenida T63", "62934012231", horarios));

        obterLocaisAtendimento(mFirebaseDatabaseReference);

    }

    private void criarLocalAtendimento(DatabaseReference mFirebaseDatabaseReference, LocalAtendimento localAtendimento) {
        //TODO: pegar o id do novo local atraves do ultimo id registrado do banco de dados
        String idLocal = "0";

        mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).child(idLocal).setValue(localAtendimento);
    }

    private  void obterLocaisAtendimento(DatabaseReference mFirebaseDatabaseReference) {
        mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<LocalAtendimento> locaisAtendimento = (List<LocalAtendimento>) dataSnapshot.getValue();
                Log.w(TAG," e o locais cadastrados sao: " + locaisAtendimento);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "obterLocaisAtendimento: onCancelled", databaseError.toException());

            }
        });
    }
}
