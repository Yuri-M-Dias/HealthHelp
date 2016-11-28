package br.ufg.inf.pes.healthhelp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Agenda;
import br.ufg.inf.pes.healthhelp.model.Atendimento;
import br.ufg.inf.pes.healthhelp.model.Atuacao;
import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.inf.pes.healthhelp.model.event.PaginadorDiasEvent;
import br.ufg.pes.healthhelp.R;

public abstract class AgendaFragment extends Fragment {

    public static final String ARG_DATA = "data";
    public static final String ARG_ATUACAO = "atuacao";

    private Calendar data;
    private Atuacao atuacao;

    public Atuacao getAtuacao() {
        return atuacao;
    }

    public void setAtuacao(Atuacao atuacao) {
        this.atuacao = atuacao;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
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

        return rootView;
    }

    public abstract void onDatabaseEvent(PaginadorDiasEvent<List<Atendimento>> paginadorDiasEvent);

}
