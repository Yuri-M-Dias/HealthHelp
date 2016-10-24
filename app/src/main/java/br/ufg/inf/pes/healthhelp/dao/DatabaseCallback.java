package br.ufg.inf.pes.healthhelp.dao;

import com.google.firebase.database.DatabaseError;

/**
 * Created by cleber on 24/10/16.
 */

public interface DatabaseCallback<T> {
    public void onComplete(T object);
    public void onError(DatabaseError exception);
}
