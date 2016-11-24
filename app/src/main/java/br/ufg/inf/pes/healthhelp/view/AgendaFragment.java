package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.inf.pes.healthhelp.view.adapters.AgendaDiariaAdapter;
import br.ufg.pes.healthhelp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AgendaFragment extends Fragment {

    private static final String ARG_DATA = "data";
    private static final String ARG_AGENDA = "agenda";

    private Calendar data;
    private Agenda agenda;

    public AgendaFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AgendaFragment newInstance(Calendar data, Agenda agenda) {
        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, data);
        args.putSerializable(ARG_AGENDA, agenda);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_agenda_disponivel, container, false);
        ListView listaHorarios = (ListView) rootView.findViewById(R.id.listview_horarios);

        data = (Calendar) getArguments().getSerializable(ARG_DATA);
        agenda = (Agenda) getArguments().getSerializable(ARG_AGENDA);

        LinkedList<Atendimento> listaAtendimentos = criarListaAtendimentos(agenda);
        Log.i("qualquer", "Tamanho da lista: " + listaAtendimentos.size());

        AgendaDiariaAdapter agendaDiariaAdapter = new AgendaDiariaAdapter(getActivity(), R.layout.item_atendimento, listaAtendimentos);

        listaHorarios.setAdapter(agendaDiariaAdapter);

        return rootView;
    }

    private LinkedList<Atendimento> criarListaAtendimentos(Agenda agenda){
        LinkedList<Atendimento> atendimentosVazios = new LinkedList<>();

        for(PeriodoTempo periodoTempo: agenda.getHorariosAtendimento()) {
            Calendar horaInicial = (Calendar) data.clone();

            if(periodoTempo.getDiasSemana().contains(DayOfWeek.of(data.get(Calendar.DAY_OF_WEEK)))) {
                for (Calendar contador = (Calendar) periodoTempo.getHoraInicioCalendar().clone();
                     !contador.after(periodoTempo.getHoraFimCalendar());
                     contador.add(Calendar.MINUTE, agenda.getTempoPadraoMinutos())) {

                    horaInicial.set(Calendar.HOUR, contador.get(Calendar.HOUR));
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
        return atendimentosVazios;
    }
}
