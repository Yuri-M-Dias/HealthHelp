package br.ufg.inf.pes.healthhelp.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.pes.healthhelp.R;

/**
 * Este adapter é responsável por controlar os locais de atendimento exibidos na {@link br.ufg.inf.pes.healthhelp.view.AgendaFragment}.
 */

public class AgendaDiariaAdapter extends ArrayAdapter<Atendimento> {

    public final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");
    private List<Atendimento> locaisAtendimentoExibidos;
    private boolean eCompleta;

    public AgendaDiariaAdapter(Context context, int resource, List<Atendimento> objects, boolean eCompleta) {
        super(context, resource, objects);
        this.eCompleta = eCompleta;
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

        TextView descricao = ((TextView) convertView.findViewById(R.id.disponibilidade_item_atendimento));
        if(eCompleta) {
            if(getItem(position).getPaciente() == null) {
                descricao.setText("Horário Disponível");
                //TODO: Substituir método depreciado abaixo
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.primary_light));
            } else {
                descricao.setText("Atendimento de " + getItem(position).getPaciente().getNome());
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.icons));
            }
        } else {
            descricao.setText("[" + getItem(position).getAgenda().getNome() + "]");
        }

        return convertView;
    }

}
