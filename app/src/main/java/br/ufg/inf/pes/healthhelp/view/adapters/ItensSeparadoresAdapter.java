package br.ufg.inf.pes.healthhelp.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.pes.healthhelp.R;

/**
 * Este adapter é responsável por controlar itens genéricos que de uma lista seccionada.
 */

public abstract class ItensSeparadoresAdapter<T> extends ArrayAdapter<T> {

    protected TextView textoSeparador;
    protected TextView textoDescricao;
    protected ImageView icone;

    public ItensSeparadoresAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_separador_icone_nome_generico, parent, false);
        }

        textoSeparador = (TextView) convertView.findViewById(R.id.textView_separador);
        textoDescricao = (TextView) convertView.findViewById(R.id.textView_descricao_item_generico);
        icone = (ImageView) convertView.findViewById(R.id.icone_item_generico);

        preencherItem(position);

        return convertView;
    }

    public abstract void preencherItem(int position);
}
