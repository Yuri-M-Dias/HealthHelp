package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.inf.pes.healthhelp.model.event.PaginadorDiasEvent;
import br.ufg.inf.pes.healthhelp.view.adapters.AgendaDiariaAdapter;
import br.ufg.pes.healthhelp.R;

public class AgendaFragment extends Fragment {

    public static final String ARG_DATA = "data";
    public static final String ARG_ATUACAO = "atuacao";

    private Calendar data;
    private Atuacao atuacao;

    public AgendaFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AgendaFragment newInstance(Calendar data, Atuacao atuacao) {
        AgendaFragment fragment = new AgendaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, data);
        args.putSerializable(ARG_ATUACAO, atuacao);
        fragment.setArguments(args);
        Log.i("minha tag qualquer", "obtendo nova instancia da agenda fragment");
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
        atuacao = (Atuacao) getArguments().getSerializable(ARG_ATUACAO);

        rootView.findViewById(R.id.listview_horarios).setVisibility(View.GONE);
        rootView.findViewById(R.id.textview_sem_horarios_disponiveis).setVisibility(View.GONE);
        rootView.findViewById(R.id.imagem_sem_horarios_disponiveis).setVisibility(View.GONE);

        //agendaDisponivelActivity.getAtendimentoService().buscarAtendimentos(atuacao.getAgendas(), data);

        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDatabaseEvent(PaginadorDiasEvent<List<Atendimento>> paginadorDiasEvent) {
        final LinkedList<Atendimento> listaAtendimentosDisponiveis = criarListaAtendimentos(atuacao.getAgendas());
        if (paginadorDiasEvent.getFiltro().equals(data)) {

            //TODO: Implementar adição e remoção de períodos de tempo baseado nos horários bloqueados e liberados.

            eliminarHorariosAgendados(listaAtendimentosDisponiveis, paginadorDiasEvent.getObjeto());

            getView().findViewById(R.id.carregamento_horarios_disponiveis).setVisibility(View.GONE);
            if (listaAtendimentosDisponiveis.isEmpty()) {
                getView().findViewById(R.id.textview_sem_horarios_disponiveis).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.imagem_sem_horarios_disponiveis).setVisibility(View.VISIBLE);
            } else {
                AgendaDiariaAdapter agendaDiariaAdapter = new AgendaDiariaAdapter(getActivity(), R.layout.item_atendimento, listaAtendimentosDisponiveis);

                final ListView listaHorarios = (ListView) getView().findViewById(R.id.listview_horarios);
                listaHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //agendaDisponivelActivity.setAtendimento(listaAtendimentosDisponiveis.get(i));
                        //agendaDisponivelActivity.concluirSelecaoAtendimento();
                    }
                });
                listaHorarios.setAdapter(agendaDiariaAdapter);
                listaHorarios.setVisibility(View.VISIBLE);
            }

        }

    }

    /**
     * Cria uma lista de horários disponíveis para atendimento com base nos períodos de tempo das atuacao de uma atuação.
     *
     * @param agendas Agendas a serem utilizadas para criar a lista de atendimentos disponíveis.
     * @return Lista de atendimentos disponíveis para marcação.
     */
    private LinkedList<Atendimento> criarListaAtendimentos(List<Agenda> agendas) {
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

    /**
     * Elimina dos horários de atendimento que estão disponíveis, os horários de atendimento que já tem atendimentos marcados pra eles.
     *
     * @param atendimentosDisponiveis Lista de atendimentos com horário disponível a ser modificada.
     * @param atendimentosAgendados   Lista de atendimentos já marcados.
     */
    private void eliminarHorariosAgendados(List<Atendimento> atendimentosDisponiveis, List<Atendimento> atendimentosAgendados) {
        List<Atendimento> atendimentosARemover = new ArrayList<>();
        for (Atendimento atendimentoMarcado : atendimentosAgendados) {
            for (Atendimento atendimentoDisponivel : atendimentosDisponiveis) {
                if (atendimentoMarcado.getHoraInicio().equals(atendimentoDisponivel.getHoraInicio())) {
                    atendimentosARemover.add(atendimentoDisponivel);
                }
            }
        }

        for (Atendimento atendimentoARemover : atendimentosARemover) {
            atendimentosDisponiveis.remove(atendimentoARemover);
        }
    }

}
