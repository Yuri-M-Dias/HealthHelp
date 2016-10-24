package br.ufg.inf.pes.healthhelp.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.dao.DatabaseCallback;
import br.ufg.inf.pes.healthhelp.view.LocaisAtendimentoActivity;
import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

/**
 * Created by deassisrosal on 10/6/16.
 */

public class LocalAtendimentoService {
    private LocalAtendimentoDAO localAtendimentoDAO;

    private static final String TAG = "LocalAtendimentoService";

    public LocalAtendimentoService() {
    }

    public void solicitarListaLocaisAtendimento(DatabaseCallback callback) {
        localAtendimentoDAO = new LocalAtendimentoDAO(callback);
        localAtendimentoDAO.loadAll();

    }

}
