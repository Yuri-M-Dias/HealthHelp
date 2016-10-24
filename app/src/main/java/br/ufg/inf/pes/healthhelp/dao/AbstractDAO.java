package br.ufg.inf.pes.healthhelp.dao;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by deassisrosal on 10/18/16.
 */

public abstract class AbstractDAO<T> implements InterfaceDAO<T>{
    private String daoTag;
    private String daoHealthHelpChild;

    private DatabaseReference mFirebaseDatabaseReference;
    private ChildEventListener mFirebaseChildEventListener;

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
