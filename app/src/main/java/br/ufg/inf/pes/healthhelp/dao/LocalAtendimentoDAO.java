package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
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
import br.ufg.inf.pes.healthhelp.service.LocalAtendimentoService;

/**
 * Created by deassisrosal on 9/29/16.
 */
public class LocalAtendimentoDAO {

    private DatabaseReference mFirebaseDatabaseReference;

    private static final String TAG = "LocalAtendimentoDao";
    private static final String HEALTHHELP_CHILD = "localAtendimento";
    private List<LocalAtendimento> mLocaisAtendimento;
    private LocalAtendimentoService mLocalAtendimentoService;

    public List<LocalAtendimento> getmLocaisAtendimento() {
        return mLocaisAtendimento;
    }

    public void setmLocaisAtendimento(List<LocalAtendimento> mLocaisAtendimento) {
        this.mLocaisAtendimento = mLocaisAtendimento;
    }

    public LocalAtendimentoDAO(LocalAtendimentoService localAtendimentoService ) {
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mLocalAtendimentoService = localAtendimentoService;
        mLocaisAtendimento = new ArrayList<>();
        carregarLocaisAtendimento(mFirebaseDatabaseReference);
        // mock do primeiro registro de horariosAtendimento de um local
       /* ArrayList<PeriodoTempo> horarios = new ArrayList<>();
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
        );*/
        //inserindo mock de primeiro localAtendimento
        //criarLocalAtendimento(mFirebaseDatabaseReference, new LocalAtendimento("Hospital Santa Cecília", "Avenida T63", "62934012231", horarios));


    }

    public void criarLocalAtendimento(DatabaseReference mFirebaseDatabaseReference, LocalAtendimento localAtendimento) {
        //TODO: pegar o id do novo local atraves do ultimo id registrado do banco de dados?
        //String idLocal = "0";

        mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).setValue(localAtendimento);
    }

    private void carregarLocaisAtendimento(DatabaseReference mFirebaseDatabaseReference) {
        Log.w(TAG,"carregarLocaisAtendimento aberto. ");

        mFirebaseDatabaseReference.child(HEALTHHELP_CHILD).addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        mLocaisAtendimento.add(dataSnapshot.getValue(LocalAtendimento.class));
                        Log.w(TAG,"os locais cadastrados sao: " + mLocaisAtendimento);
                        mLocalAtendimentoService.receberLocaisAtendimento(mLocaisAtendimento);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "carregarLocaisAtendimento: onCancelled", databaseError.toException());
                        mLocalAtendimentoService.receberLocaisAtendimento(null);
                    }
                });
    }
}
