package br.ufg.inf.pes.healthhelp.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.pes.healthhelp.R;

/**
 * Este adapter é responsável por controlar os locais de atendimento exibidos na {@link br.ufg.inf.pes.healthhelp.view.AgendaFragment}.
 */

public class AgendaDiariaCompletaAdapter extends AgendaDiariaAdapter {

    public AgendaDiariaCompletaAdapter(Context context, int resource, Atuacao atuacao, List<Atendimento> atendimentosMarcados, Calendar data) {
        super(context, resource, new ArrayList<>(), data);
        List<Atendimento> atendimentosPossiveis = criarListaAtendimentos(atuacao.getAgendas());
        List<Atendimento> atendimentosCompletos = combinarAtendimentos(atendimentosPossiveis, atendimentosMarcados);
        getFonteDados().clear();
        getFonteDados().addAll(atendimentosCompletos);
        getFonteDados().addAll(atuacao.getHorariosAlmoco());

        Collections.sort(getFonteDados(), new Comparator() {
            @Override
            public int compare(Object objeto, Object outroObjeto) {
                Calendar horaInicioObjeto, horaInicioOutroObjeto;
                if(objeto instanceof Atendimento){
                    horaInicioObjeto = ((Atendimento) objeto).getHoraInicioCalendar();
                } else {
                    horaInicioObjeto = ((PeriodoTempo) objeto).getHoraInicioCalendar();
                }

                if(objeto instanceof Atendimento){
                    horaInicioOutroObjeto = ((Atendimento) objeto).getHoraInicioCalendar();
                } else {
                    horaInicioOutroObjeto = ((PeriodoTempo) objeto).getHoraInicioCalendar();
                }
                return horaInicioObjeto.compareTo(horaInicioOutroObjeto);
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

        String textoHorario, textoDescricao = "";
        int corBackground = 0;
        if(getItem(position) instanceof Atendimento) {
            Atendimento atendimento = (Atendimento) getItem(position);
            textoHorario = construirStringHorario(atendimento .getHoraInicioCalendar(), atendimento .getHoraFimCalendar());
            if (atendimento.getPaciente() == null) {
                textoDescricao = "Horário Disponível";
                corBackground = R.color.primary_light;
            } else {
                textoDescricao = "Atendimento de " + atendimento.getPaciente().getNome();
                corBackground = R.color.icons;
            }
        } else if(getItem(position) instanceof PeriodoTempo){
            PeriodoTempo periodoTempo = (PeriodoTempo) getItem(position);
            textoHorario = construirStringHorario(periodoTempo.getHoraInicioCalendar(), periodoTempo.getHoraFimCalendar());
            textoDescricao = "HORÁRIO DE ALMOÇO";
            corBackground = R.color.divider;
        } else {
            Log.e(this.getClass().getCanonicalName(), "Tipo de dado não reconhecido para preenchimento de célula: " + getItem(position).getClass());
            textoHorario = "-";
            textoDescricao = "Desconhecido";
            corBackground = R.color.secondary_text;
        }

        TextView horarioCompletoAtendimento = (TextView) convertView.findViewById(R.id.horario_atendimento_agenda);
        horarioCompletoAtendimento.setText(textoHorario);
        TextView descricao = ((TextView) convertView.findViewById(R.id.disponibilidade_item_atendimento));
        descricao.setText(textoDescricao);

        convertView.setBackgroundColor(ContextCompat.getColor(getContext(), corBackground));

        return convertView;
    }

    /**
     * Combina a lista de atendimentos disponíveis com os atendimentos a lista de atendimentos agendados em uma única lista.
     *
     * @param atendimentosDisponiveis Lista de atendimentos com horário disponível.
     * @param atendimentosAgendados Lista de atendimentos já marcados.
     * @return lista de atendimentos combinados.
     */
    private List<Atendimento> combinarAtendimentos(List<Atendimento> atendimentosDisponiveis, List<Atendimento> atendimentosAgendados) {
        List<Atendimento> listaAtendimentosCombinados = new ArrayList<>();
        for (Atendimento atendimentoDisponivel : atendimentosDisponiveis) {
            listaAtendimentosCombinados.add(atendimentoDisponivel);
            for (Atendimento atendimentoMarcado : atendimentosAgendados) {
                if (atendimentoMarcado.getHoraInicio().equals(atendimentoDisponivel.getHoraInicio())) {
                    listaAtendimentosCombinados.set(listaAtendimentosCombinados.size() - 1, atendimentoMarcado);
                }
            }
        }

        return listaAtendimentosCombinados;

    }

}
