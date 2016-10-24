package br.ufg.inf.pes.healthhelp.dao;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by deassisrosal on 10/18/16.
 */

public abstract class AbstractDAO<T> implements InterfaceDAO<T>{
    private String daoTag;
    private String daoHealthHelpChild;
    private DatabaseCallback databaseCallback;

    private DatabaseReference mFirebaseDatabaseReference;
    private ChildEventListener mFirebaseChildEventListener;

    public AbstractDAO(String daoTag, String daoHealthHelpChild, DatabaseCallback databaseCallback) {
        this.daoTag = daoTag;
        this.daoHealthHelpChild = daoHealthHelpChild;
        this.databaseCallback = databaseCallback;
        this.mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    protected String getDaoHealthHelpChild() {
        return daoHealthHelpChild;
    }

    protected void setDaoHealthHelpChild(String daoHealthHelpChild) {
        this.daoHealthHelpChild = daoHealthHelpChild;
    }

    protected String getDaoTag() {
        return daoTag;
    }

    protected void setDaoTag(String daoTag) {
        this.daoTag = daoTag;
    }

    public DatabaseCallback getDatabaseCallback() {
        return databaseCallback;
    }

    public void setDatabaseCallback(DatabaseCallback databaseCallback) {
        this.databaseCallback = databaseCallback;
    }

    protected DatabaseReference getmFirebaseDatabaseReference() {
        return mFirebaseDatabaseReference;
    }

    protected void setmFirebaseDatabaseReference(DatabaseReference getmFirebaseDatabaseReference) {
        this.mFirebaseDatabaseReference = getmFirebaseDatabaseReference;
    }

    protected ChildEventListener getmFirebaseChildEventListener() {
        return mFirebaseChildEventListener;
    }

    protected void setmFirebaseChildEventListener(ChildEventListener mFirebaseChildEventListener) {
        this.mFirebaseChildEventListener = mFirebaseChildEventListener;
    }
}
