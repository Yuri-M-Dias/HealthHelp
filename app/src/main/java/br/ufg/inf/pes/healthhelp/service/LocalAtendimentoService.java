package br.ufg.inf.pes.healthhelp.service;

import android.util.Log;

import java.util.ArrayList;

import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

/**
 * Created by deassisrosal on 10/6/16.
 */

public class LocalAtendimentoService {
    private LocalAtendimentoDAO localAtendimentoDAO;

    private static final String TAG = "LocalAtendimentoService";

    public LocalAtendimentoService() {
        localAtendimentoDAO = new LocalAtendimentoDAO();
    }

    public ArrayList<LocalAtendimento> obterLocaisAtendimento() {
        ArrayList<LocalAtendimento> localAtendimentos = (ArrayList) localAtendimentoDAO.getLocaisAtendimento();
        if( localAtendimentos != null) {
            return localAtendimentos;
        }
        else{
            Log.e(TAG, "obterLocaisAtendimento: local null" + localAtendimentos);
        }
    }
}
