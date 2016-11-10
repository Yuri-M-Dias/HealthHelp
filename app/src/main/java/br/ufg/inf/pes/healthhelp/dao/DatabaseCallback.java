package br.ufg.inf.pes.healthhelp.dao;

import com.google.firebase.database.DatabaseError;

public interface DatabaseCallback<T> {
    public void onComplete(T object);

    public void onError(DatabaseError exception);
}
