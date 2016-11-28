package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.event.PaginadorDiasEvent;
import br.ufg.inf.pes.healthhelp.view.adapters.AgendaDiariaCompletaAdapter;
import br.ufg.inf.pes.healthhelp.view.adapters.AgendaDiariaDisponivelAdapter;
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
        if (paginadorDiasEvent.getFiltro().equals(getData())) {

            //TODO: Implementar adição e remoção de períodos de tempo baseado nos horários bloqueados e liberados.

            AgendaDiariaCompletaAdapter agendaDiariaDisponivelAdapter = new AgendaDiariaCompletaAdapter(getActivity(), R.layout.item_atendimento, getAtuacao(), paginadorDiasEvent.getObjeto(), getData());

            getView().findViewById(R.id.carregamento_horarios_disponiveis).setVisibility(View.GONE);
            if(agendaDiariaDisponivelAdapter.getFonteDados().isEmpty()){
                TextView textView = (TextView) getView().findViewById(R.id.textview_sem_horarios_disponiveis);
                textView.setVisibility(View.VISIBLE);
                textView.setText("Não existem horários para este dia.");
                getView().findViewById(R.id.imagem_sem_horarios_disponiveis).setVisibility(View.VISIBLE);
            } else {

                final ListView listaHorarios = (ListView) getView().findViewById(R.id.listview_horarios);
                listaHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //agendaCompletaActivity.setAtendimento(listaAtendimentosDisponiveis.get(i));
                    }
                });
                listaHorarios.setAdapter(agendaDiariaDisponivelAdapter);
                listaHorarios.setVisibility(View.VISIBLE);
            }
        }

    }



}
