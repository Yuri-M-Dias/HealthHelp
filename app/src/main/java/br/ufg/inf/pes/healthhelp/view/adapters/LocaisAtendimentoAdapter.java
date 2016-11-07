package br.ufg.inf.pes.healthhelp.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.pes.healthhelp.R;

/**
 * Este adapter é responsável por controlar os locais de atendimento exibidos na {@link br.ufg.inf.pes.healthhelp.view.LocaisAtendimentoActivity}.
 */

public class LocaisAtendimentoAdapter extends ArrayAdapter<LocalAtendimento> {

    private LocalAtendimento[] locaisAtendimento;
    private List<LocalAtendimento> locaisAtendimentoExibidos;

    public LocaisAtendimentoAdapter(Context context, int resource, List<LocalAtendimento> objects) {
        super(context, resource, objects);
        locaisAtendimentoExibidos = objects;
        locaisAtendimento = new LocalAtendimento[objects.size()];
        for(int index = 0; index < objects.size(); index++) {
            locaisAtendimento[index] = objects.get(index);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.w("LocaisListAdapter:","item na posicao: " + position + " para view de locais de atendimento: " + getItem(position).getNome());

        // Check if an existing view is being reused, otherwise inflates the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_local, parent, false);
        }

        TextView nomeLocal = (TextView) convertView.findViewById(R.id.nome_local);
        TextView enderecoLocal = (TextView) convertView.findViewById(R.id.endereco_local);

        nomeLocal.setText(getItem(position).getNome());
        enderecoLocal.setText(getItem(position).getEndereco());


        return  convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<LocalAtendimento> locaisAtendimentoFiltrados = filtrarLocaisAtendimento(charSequence.toString());
                FilterResults filterResults = new FilterResults();
                filterResults.values = locaisAtendimentoFiltrados;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                locaisAtendimentoExibidos = (List<LocalAtendimento>) filterResults.values;
                Log.i(this.getClass().getName(), "exibidos: " + locaisAtendimentoExibidos.size());
                Log.i(this.getClass().getName(), "original: " + locaisAtendimento.length);
                Log.i(this.getClass().getName(), "adapter: " + LocaisAtendimentoAdapter.this.getCount());
                LocaisAtendimentoAdapter.this.clear();
                LocaisAtendimentoAdapter.this.addAll(locaisAtendimentoExibidos);
                LocaisAtendimentoAdapter.this.notifyDataSetChanged();
                Log.i(this.getClass().getName(), "exibidos: " + locaisAtendimentoExibidos.size());
                Log.i(this.getClass().getName(), "original: " + locaisAtendimento.length);
                Log.i(this.getClass().getName(), "adapter: " + LocaisAtendimentoAdapter.this.getCount());
            }
        };
    }

    private List<LocalAtendimento> filtrarLocaisAtendimento(String queryBusca) {
        List<LocalAtendimento> locaisAtendimentoFiltrados= new LinkedList<>();

        if(queryBusca.isEmpty()) {
            locaisAtendimentoFiltrados = new LinkedList<>(Arrays.asList(locaisAtendimento));
            Log.i(this.getClass().getName(), "procura vazia");
        }else {
            for (LocalAtendimento localAtendimento: locaisAtendimento) {
                if (localAtendimento.getNome().toLowerCase().contains(queryBusca.toLowerCase())) {
                    locaisAtendimentoFiltrados.add(localAtendimento);
                }
            }
            Log.i(this.getClass().getName(), "procura com " + locaisAtendimentoFiltrados.size() + " resultados");
        }
        return locaisAtendimentoFiltrados;
    }
}
