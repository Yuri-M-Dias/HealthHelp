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

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Calendar;

import br.ufg.inf.pes.healthhelp.model.PeriodoTempo;
import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;
import br.ufg.pes.healthhelp.R;

import static br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek.FRIDAY;
import static br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek.MONDAY;
import static br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek.SATURDAY;
import static br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek.SUNDAY;
import static br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek.THURSDAY;
import static br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek.TUESDAY;
import static br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek.WEDNESDAY;

public class PeriodoTempoView extends LinearLayout {

    private static final String TAG = PeriodoTempoView.class.getCanonicalName();

    private PeriodoTempo periodoTempo;
    private ViewGroup container;

    public PeriodoTempoView(Context context, PeriodoTempo periodoTempo, ViewGroup container) {
        super(context);
        this.periodoTempo = periodoTempo;
        this.container = container;

        init();
    }

    public void init() {
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

        preencherView();
    }

    private void preencherView() {
        if (periodoTempo.getDataInicioCalendar() != null) {
            ((Button) findViewById(R.id.botao_data_inicio)).setText(periodoTempo.getDataInicio());
        }
        if (periodoTempo.getDataFimCalendar() != null) {
            ((Button) findViewById(R.id.botao_data_final)).setText(periodoTempo.getDataFim());
        }

        if (periodoTempo.getHoraInicioCalendar() != null) {
            ((Button) findViewById(R.id.botao_hora_inicio)).setText(periodoTempo.getHoraInicio());
        }
        if (periodoTempo.getHoraFimCalendar() != null) {
            ((Button) findViewById(R.id.botao_hora_final)).setText(periodoTempo.getHoraFim());
        }

        for (String diaSemana : periodoTempo.getDiasSemana()) {
            int recurso;

            DayOfWeek dia = DayOfWeek.of(Integer.valueOf(diaSemana));
            switch (dia) {
                case SUNDAY:
                    recurso = R.id.botao_domingo;
                    break;
                case MONDAY:
                    recurso = R.id.botao_segunda;
                    break;
                case TUESDAY:
                    recurso = R.id.botao_terca;
                    break;
                case WEDNESDAY:
                    recurso = R.id.botao_quarta;
                    break;
                case THURSDAY:
                    recurso = R.id.botao_quinta;
                    break;
                case FRIDAY:
                    recurso = R.id.botao_sexta;
                    break;
                default:
                    recurso = R.id.botao_sabado;
                    break;
            }
            ((ToggleButton) findViewById(recurso)).setChecked(true);

        }

    }

    public void remover() {
        container.removeView(this);
    }

    public void pickDate(final View view) {
        if (view.getId() == R.id.botao_data_inicio || view.getId() == R.id.botao_data_final) {
            final Calendar dataArmazenada;
            if (view.getId() == R.id.botao_data_inicio) {
                if (periodoTempo.getDataInicioCalendar() == null) {
                    periodoTempo.setDataInicio(Calendar.getInstance());
                }
                dataArmazenada = periodoTempo.getDataInicioCalendar();
            } else {
                if (periodoTempo.getDataFimCalendar() == null) {
                    periodoTempo.setDataFim(Calendar.getInstance());
                }
                dataArmazenada = periodoTempo.getDataFimCalendar();
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dataArmazenada.set(i, i1, i2);
                        ((Button) view).setText(PeriodoTempo.DATE_FORMATTER.format(dataArmazenada.getTime()));
                        ((Button) view).setError(null);
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


    public void pickTime(final View view) {
        if (view.getId() == R.id.botao_hora_inicio || view.getId() == R.id.botao_hora_final) {
            final Calendar horaArmazenada;
            if (view.getId() == R.id.botao_hora_inicio) {
                if (periodoTempo.getHoraInicioCalendar() == null) {
                    periodoTempo.setHoraInicio(Calendar.getInstance());
                }
                horaArmazenada = periodoTempo.getHoraInicioCalendar();
            } else {
                if (periodoTempo.getHoraFimCalendar() == null) {
                    periodoTempo.setHoraFim(Calendar.getInstance());
                }
                horaArmazenada = periodoTempo.getHoraFimCalendar();
            }

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int horas, int minutos) {
                        horaArmazenada.set(horaArmazenada.get(Calendar.YEAR),
                            horaArmazenada.get(Calendar.MONTH),
                            horaArmazenada.get(Calendar.DAY_OF_MONTH), horas, minutos);
                        ((Button) view).setText(PeriodoTempo.TIME_FORMATTER.format(horaArmazenada.getTime()));
                        ((Button) view).setError(null);
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

    private void configurarBotoesDiasSemana() {
        View.OnClickListener acaoBotaoDiaSemana = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DayOfWeek diaSemana = null;
                switch (view.getId()) {
                    case R.id.botao_domingo:
                        diaSemana = SUNDAY;
                        break;
                    case R.id.botao_segunda:
                        diaSemana = MONDAY;
                        break;
                    case R.id.botao_terca:
                        diaSemana = TUESDAY;
                        break;
                    case R.id.botao_quarta:
                        diaSemana = WEDNESDAY;
                        break;
                    case R.id.botao_quinta:
                        diaSemana = THURSDAY;
                        break;
                    case R.id.botao_sexta:
                        diaSemana = FRIDAY;
                        break;
                    case R.id.botao_sabado:
                        diaSemana = SATURDAY;
                        break;

                }

                if (((ToggleButton) view).isChecked()) {
                    periodoTempo.getDiasSemana().add(String.valueOf(diaSemana.getValue()));
                } else {
                    periodoTempo.getDiasSemana().remove(String.valueOf(diaSemana.getValue()));
                }
            }
        };

        LinearLayout containerDiasSemana = (LinearLayout) findViewById(R.id.container_dias_semana);
        for (int posicao = 0; posicao < containerDiasSemana.getChildCount(); posicao++) {
            View botao = containerDiasSemana.getChildAt(posicao);
            Log.i(TAG, botao.getClass().getCanonicalName());
            if (botao instanceof ToggleButton) {
                botao.setOnClickListener(acaoBotaoDiaSemana);
            }
        }
    }

    public PeriodoTempo getPeriodoTempo() {
        return periodoTempo;
    }

    public boolean validarFormulario() {
        Button foco = null;
        if (periodoTempo.getDiasSemana().isEmpty()) {
            new MaterialDialog.Builder(getContext())
                .title(R.string.erro_titulo_horario_atendimento_incompleto)
                .content(R.string.erro_dias_semana_vazio)
                .positiveText("OK")
                .show();
            return false;
        } else if (periodoTempo.getHoraInicioCalendar() == null) {
            foco = (Button) findViewById(R.id.botao_hora_inicio);
            foco.requestFocus();
            foco.setError(getContext().getString(R.string.erro_campo_obrigatorio));
            return false;
        } else if (periodoTempo.getHoraFimCalendar() == null) {
            foco = (Button) findViewById(R.id.botao_hora_final);
            foco.requestFocus();
            foco.setError(getContext().getString(R.string.erro_campo_obrigatorio));
            return false;
        }

        return true;
    }
}
