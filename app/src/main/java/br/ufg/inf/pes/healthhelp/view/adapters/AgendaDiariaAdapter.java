package br.ufg.inf.pes.healthhelp.view.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;

/**
 * Este adapter é responsável por controlar os locais de atendimento exibidos na {@link br.ufg.inf.pes.healthhelp.view.AgendaFragment}.
 */

public class AgendaDiariaAdapter<T> extends ArrayAdapter<T> {

    public final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");
    private List<T> fonteDados;
    private Calendar data;

    public AgendaDiariaAdapter(Context context, int resource, List<T> fonteDados, Calendar data) {
        super(context, resource, fonteDados);
        this.fonteDados = fonteDados;
        this.data = data;
    }

    public List<T> getFonteDados() {
        return fonteDados;
    }


    /**
     * Cria uma lista de horários disponíveis para atendimento com base nos períodos de tempo das atuacao de uma atuação.
     *
     * @param agendas Agendas a serem utilizadas para criar a lista de atendimentos disponíveis.
     * @return Lista de atendimentos disponíveis para marcação.
     */
    protected LinkedList<Atendimento> criarListaAtendimentos(List<Agenda> agendas) {
        LinkedList<Atendimento> atendimentosVazios = new LinkedList<>();

        for (Agenda agenda : agendas) {
            for (PeriodoTempo periodoTempo : agenda.getHorariosAtendimento()) {
                Calendar horaInicial = (Calendar) data.clone();

                if (periodoTempo.getDiasSemana().contains(DayOfWeek.of(data.get(Calendar.DAY_OF_WEEK)))) {
                    for (Calendar contador = (Calendar) periodoTempo.getHoraInicioCalendar().clone();
                         !contador.after(periodoTempo.getHoraFimCalendar());
                         contador.add(Calendar.MINUTE, agenda.getTempoPadraoMinutos())) {

                        horaInicial.set(Calendar.HOUR_OF_DAY, contador.get(Calendar.HOUR_OF_DAY));
                        horaInicial.set(Calendar.MINUTE, contador.get(Calendar.MINUTE));

                        Atendimento atendimento = new Atendimento();
                        atendimento.setAgenda(agenda);

                        atendimento.setHoraInicio((Calendar) horaInicial.clone());

                        horaInicial.add(Calendar.MINUTE, agenda.getTempoPadraoMinutos());
                        atendimento.setHoraFim((Calendar) horaInicial.clone());

                        atendimentosVazios.add(atendimento);
                    }
                }
            }
        }
        return atendimentosVazios;
    }

    protected String construirStringHorario(Calendar horaInicio, Calendar horaFim){
        return TIME_FORMATTER.format(horaInicio.getTime()) + "\n" + TIME_FORMATTER.format(horaFim.getTime());
    }

}
