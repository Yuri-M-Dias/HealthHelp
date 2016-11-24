package br.ufg.inf.pes.healthhelp.model;


import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Classe base para permitir maior abstração, e menor repetição dos métodos utilizados
 */
public abstract class BaseObject implements Serializable {

    @Exclude
    private String id;

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

}
