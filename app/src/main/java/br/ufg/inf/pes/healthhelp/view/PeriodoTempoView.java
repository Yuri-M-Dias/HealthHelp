package br.ufg.inf.pes.healthhelp.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.pes.healthhelp.R;

public class PeriodoTempoView extends LinearLayout {

    private static final String TAG = PeriodoTempoView.class.getCanonicalName();
    private static final String PERIODO_TEMPO_PARAM = "column-count";

    private PeriodoTempo periodoTempo;
    private ViewGroup container;

    public PeriodoTempoView(Context context, PeriodoTempo periodoTempo, ViewGroup container) {
        super(context);
        this.periodoTempo = periodoTempo;
        this.container = container;

        init();
    }

    public void init(){
        inflate(getContext(), R.layout.view_periodo_tempo, this);

        configurarBotoesDiasSemana();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remover();
            }
        };

        findViewById(R.id.botao_remover_horario_atendimento).setOnClickListener(onClickListener);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(view);
            }
        };

        findViewById(R.id.botao_data_inicio).setOnClickListener(onClickListener);
        findViewById(R.id.botao_data_final).setOnClickListener(onClickListener);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(view);
            }
        };

        findViewById(R.id.botao_hora_inicio).setOnClickListener(onClickListener);
        findViewById(R.id.botao_hora_final).setOnClickListener(onClickListener);
    }

    public void remover(){
        container.removeView(this);
    }

    /*
    public static PeriodoTempoFragment newInstance(PeriodoTempo periodoTempo) {
        PeriodoTempoFragment fragment = new PeriodoTempoFragment();
        Bundle args = new Bundle();
        args.putSerializable(PERIODO_TEMPO_PARAM, periodoTempo);
        fragment.setArguments(args);
        return fragment;
    }*/
/*
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
        View view = inflater.inflate(R.layout.view_periodo_tempo, container, false);

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configurarBotoesDiasSemana();
    }
    */

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

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
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

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
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

    private void configurarBotoesDiasSemana(){
        Log.e(TAG, "Configurando botoes de dia da semana");
        View.OnClickListener acaoBotaoDiaSemana = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DayOfWeek diaSemana = null;
                switch (view.getId()){
                    case R.id.botao_domingo:
                        diaSemana = DayOfWeek.SUNDAY;
                        break;
                    case R.id.botao_segunda:
                        diaSemana = DayOfWeek.MONDAY;
                        break;
                    case R.id.botao_terca:
                        diaSemana = DayOfWeek.TUESDAY;
                        break;
                    case R.id.botao_quarta:
                        diaSemana = DayOfWeek.WEDNESDAY;
                        break;
                    case R.id.botao_quinta:
                        diaSemana = DayOfWeek.THURSDAY;
                        break;
                    case R.id.botao_sexta:
                        diaSemana = DayOfWeek.FRIDAY;
                        break;
                    case R.id.botao_sabado:
                        diaSemana = DayOfWeek.SATURDAY;
                        break;

                }

                Log.i(TAG, "tamanho antes: " + periodoTempo.getDiasSemana().size());
                if(((ToggleButton)view).isChecked()) {
                    Log.i(TAG, "adicionando: " + diaSemana);
                    periodoTempo.getDiasSemana().add(diaSemana);
                } else {
                    Log.i(TAG, "removendo: " + diaSemana);
                    periodoTempo.getDiasSemana().remove(diaSemana);
                }
                Log.i(TAG, "tamanho depois: " + periodoTempo.getDiasSemana().size());
            }
        };

        LinearLayout containerDiasSemana = (LinearLayout) findViewById(R.id.container_dias_semana);
        for(int posicao = 0; posicao < containerDiasSemana.getChildCount(); posicao++){
            View botao = containerDiasSemana.getChildAt(posicao);
            Log.i(TAG, botao.getClass().getCanonicalName());
            if(botao instanceof ToggleButton) {
                botao.setOnClickListener(acaoBotaoDiaSemana);
            }
        }
    }
}
