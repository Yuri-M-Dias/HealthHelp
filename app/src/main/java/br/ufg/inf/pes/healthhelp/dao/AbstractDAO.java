package br.ufg.inf.pes.healthhelp.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class AbstractDAO<T> implements InterfaceDAO<T> {

    public final String TAG;
    public final String DATABASE_CHILD;

    private DatabaseCallback databaseCallback;
    private DatabaseReference databaseReference;

    public AbstractDAO(String TAG, String DATABASE_CHILD) {
        this.TAG = TAG;
        this.DATABASE_CHILD = DATABASE_CHILD;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseCallback getDatabaseCallback() {
        return databaseCallback;
    }

    public void setDatabaseCallback(DatabaseCallback databaseCallback) {
        this.databaseCallback = databaseCallback;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference getmFirebaseDatabaseReference) {
        this.databaseReference = getmFirebaseDatabaseReference;
    }

}
