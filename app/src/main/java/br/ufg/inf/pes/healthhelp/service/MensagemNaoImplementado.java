package br.ufg.inf.pes.healthhelp.service;

import android.content.Context;
import android.widget.Toast;

public class MensagemNaoImplementado {

    public static void MostraMensagemNaoImplementado(Context context, CharSequence text){

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
