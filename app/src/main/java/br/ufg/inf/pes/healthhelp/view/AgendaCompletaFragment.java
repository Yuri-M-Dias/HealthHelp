package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.inf.pes.healthhelp.model.event.PaginadorDiasEvent;
import br.ufg.inf.pes.healthhelp.view.adapters.AgendaDiariaAdapter;
import br.ufg.pes.healthhelp.R;

public class AgendaCompletaFragment extends AgendaFragment {

    private AgendaCompletaActivity agendaCompletaActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        agendaCompletaActivity = ((AgendaCompletaActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        agendaCompletaActivity.getAtendimentoService().buscarAtendimentos(getAtuacao().getAgendas(), getData());

        return view;
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDatabaseEvent(PaginadorDiasEvent<List<Atendimento>> paginadorDiasEvent) {
        final LinkedList<Atendimento> listaAtendimentosDisponiveis = criarListaAtendimentos(getAtuacao().getAgendas());
        if (paginadorDiasEvent.getFiltro().equals(getData())) {

            //TODO: Implementar adição e remoção de períodos de tempo baseado nos horários bloqueados e liberados.

            getView().findViewById(R.id.carregamento_horarios_disponiveis).setVisibility(View.GONE);
            AgendaDiariaAdapter agendaDiariaAdapter = new AgendaDiariaAdapter(getActivity(), R.layout.item_atendimento,
                combinarAtendimentos(listaAtendimentosDisponiveis, paginadorDiasEvent.getObjeto()), true);

            final ListView listaHorarios = (ListView) getView().findViewById(R.id.listview_horarios);
            listaHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    agendaCompletaActivity.setAtendimento(listaAtendimentosDisponiveis.get(i));
                }
            });
            listaHorarios.setAdapter(agendaDiariaAdapter);
            listaHorarios.setVisibility(View.VISIBLE);

        }

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
