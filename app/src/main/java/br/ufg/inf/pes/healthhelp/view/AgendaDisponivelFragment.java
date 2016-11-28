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

public class AgendaDisponivelFragment extends AgendaFragment {

    private AgendaDisponivelActivity agendaDisponivelActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agendaDisponivelActivity = ((AgendaDisponivelActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        agendaDisponivelActivity.getAtendimentoService().buscarAtendimentos(getAtuacao().getAgendas(), getData());

        return view;
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDatabaseEvent(PaginadorDiasEvent<List<Atendimento>> paginadorDiasEvent) {
        final LinkedList<Atendimento> listaAtendimentosDisponiveis = criarListaAtendimentos(getAtuacao().getAgendas());
        if (paginadorDiasEvent.getFiltro().equals(getData())) {

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
                        agendaDisponivelActivity.setAtendimento(listaAtendimentosDisponiveis.get(i));
                        agendaDisponivelActivity.concluirSelecaoAtendimento();
                    }
                });
                listaHorarios.setAdapter(agendaDiariaAdapter);
                listaHorarios.setVisibility(View.VISIBLE);
            }

        }

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
