package br.ufg.inf.pes.healthhelp.service;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.view.LocaisAtendimentoActivity;
import br.ufg.inf.pes.healthhelp.dao.LocalAtendimentoDAO;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;

/**
 * Created by deassisrosal on 10/6/16.
 */

public class LocalAtendimentoService {
    private LocalAtendimentoDAO localAtendimentoDAO;
    private LocaisAtendimentoActivity locaisAtendimentoActivity;

    private static final String TAG = "LocalAtendimentoService";

    public LocalAtendimentoService(LocaisAtendimentoActivity locaisAtendimentoActivity) {
        this.localAtendimentoDAO = new LocalAtendimentoDAO(this);
        this.locaisAtendimentoActivity = locaisAtendimentoActivity;
    }

    public void configuralocaisAtendimento(Context context) {

    }

    /**
     * disparado pelo onStateChanged do firebase após uma requisição por dados no firebase é feita
     * @param locaisAtendimento
     */
    public void receberLocaisAtendimento(List<LocalAtendimento> locaisAtendimento) {
        if( locaisAtendimento != null) {
            locaisAtendimentoActivity.carregar(locaisAtendimento);
        }
        else{
            Log.e(TAG, "obterLocaisAtendimento: locais não obtidos: localAtendimentos: " + locaisAtendimento);
            locaisAtendimentoActivity.carregar(new ArrayList<LocalAtendimento>());
        }
    }
}
