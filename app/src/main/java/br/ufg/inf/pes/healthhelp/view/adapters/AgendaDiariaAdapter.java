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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.LocalAtendimento;
import br.ufg.pes.healthhelp.R;

/**
 * Este adapter é responsável por controlar os locais de atendimento exibidos na {@link br.ufg.inf.pes.healthhelp.view.AgendaFragment}.
 */

public class AgendaDiariaAdapter extends ArrayAdapter<Atendimento> {

    private List<Atendimento> locaisAtendimentoExibidos;
    public final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");

    public AgendaDiariaAdapter(Context context, int resource, List<Atendimento> objects) {
        super(context, resource, objects);
        locaisAtendimentoExibidos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflates the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_atendimento, parent, false);
        }

        TextView horarioCompletoAtendimento = (TextView) convertView.findViewById(R.id.horario_atendimento_agenda);
        horarioCompletoAtendimento.setText(TIME_FORMATTER.format(getItem(position).getHoraInicioCalendar().getTime())
            + "\n" + TIME_FORMATTER.format(getItem(position).getHoraFimCalendar().getTime()));

        preencherTextoDisponibilidade((TextView) convertView.findViewById(R.id.disponibilidade_item_atendimento), position);

        return convertView;
    }

    private void preencherTextoDisponibilidade(TextView disponibilidade, int position) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(getItem(position).getAgenda().getNome());
        stringBuilder.append("]: ");
        stringBuilder.append((getItem(position).getPaciente() == null)? "Disponível": "Indisponível");

        disponibilidade.setText(stringBuilder.toString());

    }
}
