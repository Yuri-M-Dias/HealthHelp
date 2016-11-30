package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.event.PaginadorDiasEvent;
import br.ufg.inf.pes.healthhelp.view.adapters.AgendaDiariaDisponivelAdapter;
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
        if (paginadorDiasEvent.getFiltro().equals(getData())) {

            final AgendaDiariaDisponivelAdapter agendaDiariaDisponivelAdapter = new AgendaDiariaDisponivelAdapter(
                getActivity(), R.layout.item_atendimento, getAtuacao(), paginadorDiasEvent.getObjeto(), getData());

            getView().findViewById(R.id.carregamento_horarios_disponiveis).setVisibility(View.GONE);
            if (agendaDiariaDisponivelAdapter.getFonteDados().isEmpty()) {
                getView().findViewById(R.id.textview_sem_horarios_disponiveis).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.imagem_sem_horarios_disponiveis).setVisibility(View.VISIBLE);
            } else {
                final ListView listaHorarios = (ListView) getView().findViewById(R.id.listview_horarios);
                listaHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        agendaDisponivelActivity.setAtendimento(agendaDiariaDisponivelAdapter.getFonteDados().get(i));
                        agendaDisponivelActivity.concluirSelecaoAtendimento();
                    }
                });
                listaHorarios.setAdapter(agendaDiariaDisponivelAdapter);
                listaHorarios.setVisibility(View.VISIBLE);
            }

        }

    }

}
