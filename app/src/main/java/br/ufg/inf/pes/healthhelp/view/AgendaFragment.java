package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.inf.pes.healthhelp.model.event.AtendimentoEvent;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;
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

    private AgendaDisponivelActivity agendaDisponivelActivity;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        agendaDisponivelActivity = ((AgendaDisponivelActivity) getActivity());

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_agenda_disponivel, container, false);

        data = (Calendar) getArguments().getSerializable(ARG_DATA);
        agenda = (Agenda) getArguments().getSerializable(ARG_AGENDA);

        rootView.findViewById(R.id.listview_horarios).setVisibility(View.GONE);

        final LinkedList<Atendimento> listaAtendimentos = criarListaAtendimentos(agenda);
        if(listaAtendimentos.isEmpty()){
            rootView.findViewById(R.id.carregamento_horarios_disponiveis).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.textview_sem_horarios_disponiveis).setVisibility(View.GONE);
            rootView.findViewById(R.id.imagem_sem_horarios_disponiveis).setVisibility(View.GONE);

            agendaDisponivelActivity.getAtendimentoService().buscarAtendimentos(agenda, data);
        }
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDatabaseEvent(AtendimentoEvent<List<Atendimento>> atendimentosMarcados) {
        final LinkedList<Atendimento> listaAtendimentosDisponiveis = criarListaAtendimentos(agenda);
        if(atendimentosMarcados.getDiaOcorrencia().equals(data)){

            List<Atendimento> atendimentosARemover = new ArrayList<>();
            for(Atendimento atendimentoMarcado: atendimentosMarcados.getObjeto()) {
                for (Atendimento atendimentoDisponivel : listaAtendimentosDisponiveis) {
                    if(atendimentoMarcado.getHoraInicio().equals(atendimentoDisponivel.getHoraInicio())){
                        atendimentosARemover.add(atendimentoDisponivel);
                    }
                }
            }

            for(Atendimento atendimentoARemover : atendimentosARemover) {
                listaAtendimentosDisponiveis.remove(atendimentoARemover);
            }

            final ListView listaHorarios = (ListView) getView().findViewById(R.id.listview_horarios);
            //final ListView listaHorarios = new ListView(getActivity());
            listaHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    agendaDisponivelActivity.setAtendimento(listaAtendimentosDisponiveis.get(i));
                    agendaDisponivelActivity.concluirSelecaoAtendimento();
                }
            });

            AgendaDiariaAdapter agendaDiariaAdapter = new AgendaDiariaAdapter(getActivity(), R.layout.item_atendimento, listaAtendimentosDisponiveis);

            listaHorarios.setAdapter(agendaDiariaAdapter);

            getView().findViewById(R.id.carregamento_horarios_disponiveis).setVisibility(View.GONE);
            listaHorarios.setVisibility(View.VISIBLE);

        }

    }

    private LinkedList<Atendimento> criarListaAtendimentos(Agenda agenda){
        LinkedList<Atendimento> atendimentosVazios = new LinkedList<>();

        for(PeriodoTempo periodoTempo: agenda.getHorariosAtendimento()) {
            Calendar horaInicial = (Calendar) data.clone();

            if(periodoTempo.getDiasSemana().contains(DayOfWeek.of(data.get(Calendar.DAY_OF_WEEK)))) {
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
        return atendimentosVazios;
    }
}
