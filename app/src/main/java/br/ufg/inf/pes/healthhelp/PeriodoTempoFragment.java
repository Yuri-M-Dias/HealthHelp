package br.ufg.inf.pes.healthhelp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.pes.healthhelp.R;

public class PeriodoTempoFragment extends Fragment {

    private static final String TAG = PeriodoTempoFragment.class.getCanonicalName();
    private static final String PERIODO_TEMPO_PARAM = "column-count";

    private PeriodoTempo periodoTempo;

    public PeriodoTempoFragment() {
    }

    public static PeriodoTempoFragment newInstance(PeriodoTempo periodoTempo) {
        PeriodoTempoFragment fragment = new PeriodoTempoFragment();
        Bundle args = new Bundle();
        args.putSerializable(PERIODO_TEMPO_PARAM, periodoTempo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            periodoTempo = (PeriodoTempo) getArguments().getSerializable(PERIODO_TEMPO_PARAM);
        } else {
            periodoTempo = new PeriodoTempo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_periodo_tempo, container, false);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(view);
            }
        };

        view.findViewById(R.id.botao_data_inicio).setOnClickListener(onClickListener);
        view.findViewById(R.id.botao_data_final).setOnClickListener(onClickListener);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(view);
            }
        };

        view.findViewById(R.id.botao_hora_inicio).setOnClickListener(onClickListener);
        view.findViewById(R.id.botao_hora_final).setOnClickListener(onClickListener);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarDiasSemana();
            }
        };

        view.findViewById(R.id.botao_dias_semana).setOnClickListener(onClickListener);

        return view;
    }

    public void pickDate(final View view){
        if(view.getId() == R.id.botao_data_inicio || view.getId() == R.id.botao_data_final) {
            final Calendar dataArmazenada;
            if(view.getId() == R.id.botao_data_inicio) {
                if(periodoTempo.getDataInicio() == null) {
                    periodoTempo.setDataInicio(Calendar.getInstance());
                }
                dataArmazenada = periodoTempo.getDataInicio();
            } else {
                if(periodoTempo.getDataFim() == null) {
                    periodoTempo.setDataFim(Calendar.getInstance());
                }
                dataArmazenada = periodoTempo.getDataFim();
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            ((Button) view).setText(i2 + "/" + i1 + "/" + i);
                            dataArmazenada.set(i, i1, i2);
                        }
                    },
                    dataArmazenada.get(Calendar.YEAR),
                    dataArmazenada.get(Calendar.MONTH),
                    dataArmazenada.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } else {
            Log.e(TAG, "A view selecionado não é de definição de data e não pode selecionar tempo.");
        }
    }


    public void pickTime(final View view){
        if(view.getId() == R.id.botao_hora_inicio || view.getId() == R.id.botao_hora_final) {
            final Calendar horaArmazenada;
            if(view.getId() == R.id.botao_hora_inicio) {
                if(periodoTempo.getHoraInicio() == null) {
                    periodoTempo.setHoraInicio(Calendar.getInstance());
                }
                horaArmazenada = periodoTempo.getHoraInicio();
            } else {
                if(periodoTempo.getHoraFim() == null) {
                    periodoTempo.setHoraFim(Calendar.getInstance());
                }
                horaArmazenada = periodoTempo.getHoraFim();
            }

            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int horas, int minutos) {
                            ((Button) view).setText(horas + ":" + minutos);
                            horaArmazenada.set(horaArmazenada.get(Calendar.YEAR),
                                    horaArmazenada.get(Calendar.MONTH),
                                    horaArmazenada.get(Calendar.DAY_OF_MONTH), horas, minutos);
                        }
                    },
                    horaArmazenada.get(Calendar.HOUR_OF_DAY),
                    horaArmazenada.get(Calendar.MINUTE),
                    true);
            timePickerDialog.show();
        } else {
            Log.e(TAG, "A view selecionada não é de definição de hora e não pode selecionar tempo.");
        }
    }

    public void selecionarDiasSemana(){
        ArrayList<Integer> indicesDialogDiasSemanaSelecionados = new ArrayList<>();
        for(DayOfWeek diaSemana: periodoTempo.getDiasSemana()) {
            indicesDialogDiasSemanaSelecionados.add(diaSemana.getValue() - 1);
        }
        new MaterialDialog.Builder(getActivity())
                .title("Dias da Semana")
                .items(Arrays.asList(DayOfWeek.values()))
                .itemsCallbackMultiChoice(
                        indicesDialogDiasSemanaSelecionados.toArray(new Integer[indicesDialogDiasSemanaSelecionados.size()]),
                        new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] posicoesSelecionadas, CharSequence[] opcoesSelecionadas) {
                                ArrayList<DayOfWeek> diasSemana = new ArrayList<>(posicoesSelecionadas.length);
                                for(Integer posicaoSelecionada: posicoesSelecionadas){
                                    diasSemana.add(DayOfWeek.of(posicaoSelecionada + 1));
                                }
                                Button botaoDiasSemana = (Button) getActivity().findViewById(R.id.botao_dias_semana);
                                for(int posicao = 0; posicao < opcoesSelecionadas.length; posicao++){
                                    opcoesSelecionadas[posicao] = opcoesSelecionadas[posicao].subSequence(0, 3);
                                }
                                String novoLabelBotao = TextUtils.join(" - ", opcoesSelecionadas);
                                if("".equals(novoLabelBotao)) {
                                    botaoDiasSemana.setText(getString(R.string.botao_dias_semana));
                                } else {
                                    botaoDiasSemana.setText(novoLabelBotao);
                                }
                                periodoTempo.setDiasSemana(diasSemana);
                                return true;
                            }
                        })
                .positiveText("Selecionar")
                .negativeText("Cancelar")
                .show();
    }
}
