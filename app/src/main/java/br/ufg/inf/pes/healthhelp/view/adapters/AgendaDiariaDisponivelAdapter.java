package br.ufg.inf.pes.healthhelp.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.pes.healthhelp.R;

/**
 * Este adapter é responsável por controlar os locais de atendimento exibidos na {@link br.ufg.inf.pes.healthhelp.view.AgendaFragment}.
 */

public class AgendaDiariaDisponivelAdapter extends AgendaDiariaAdapter<Atendimento> {

    public AgendaDiariaDisponivelAdapter(Context context, int resource, Atuacao atuacao, List<Atendimento> atendimentosMarcados, Calendar data) {
        super(context, resource, new ArrayList<Atendimento>(), data);
        List<Atendimento> atendimentosPossiveis = criarListaAtendimentos(atuacao.getAgendas());
        List<Atendimento> atendimentosDisponiveis = eliminarHorariosAgendados(atendimentosPossiveis, atendimentosMarcados);
        getFonteDados().clear();
        getFonteDados().addAll(atendimentosDisponiveis);

        Collections.sort(getFonteDados(), new Comparator<Atendimento>() {
            @Override
            public int compare(Atendimento atendimento, Atendimento outroAtendimento) {
                return atendimento.getHoraInicioCalendar().compareTo(outroAtendimento.getHoraInicioCalendar());
            }
        });

        //notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflates the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_atendimento, parent, false);
        }

        TextView horarioCompletoAtendimento = (TextView) convertView.findViewById(R.id.horario_atendimento_agenda);
        horarioCompletoAtendimento.setText(
            construirStringHorario(getItem(position).getHoraInicioCalendar(), getItem(position).getHoraFimCalendar()));

        TextView descricao = ((TextView) convertView.findViewById(R.id.disponibilidade_item_atendimento));
        descricao.setText("[" + getItem(position).getAgenda().getNome() + "]");

        return convertView;
    }

    /**
     * Elimina dos horários de atendimento que estão disponíveis, os horários de atendimento que já tem atendimentos marcados pra eles.
     *
     * @param atendimentosDisponiveis Lista de atendimentos com horário disponível a ser modificada.
     * @param atendimentosAgendados   Lista de atendimentos já marcados.
     * @return lista de atendimentos disponíveis para marcação.
     */
    private List<Atendimento> eliminarHorariosAgendados(List<Atendimento> atendimentosDisponiveis, List<Atendimento> atendimentosAgendados) {
        List<Atendimento> atendimentosARemover = new ArrayList<>();
        for (Atendimento atendimentoMarcado : atendimentosAgendados) {
            for (Atendimento atendimentoDisponivel : atendimentosDisponiveis) {
                if (atendimentoMarcado.getHoraInicio().equals(atendimentoDisponivel.getHoraInicio())) {
                    atendimentosARemover.add(atendimentoDisponivel);
                }
            }
        }

        List<Atendimento> atendimentosDisponiveisFinal = new ArrayList<>();
        for(Atendimento atendimento: atendimentosDisponiveis){
            if(!atendimentosARemover.contains(atendimento)){
                atendimentosDisponiveisFinal.add(atendimento);
            }
        }
        return atendimentosDisponiveisFinal;

    }

}
