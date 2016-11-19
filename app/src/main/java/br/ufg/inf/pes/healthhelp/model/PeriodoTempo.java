package br.ufg.inf.pes.healthhelp.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;

public class PeriodoTempo implements Serializable {
    public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    private Calendar horaInicio;
    private Calendar horaFim;

    private Calendar dataInicio;
    private Calendar dataFim;

    private List<DayOfWeek> diasSemana;

    public PeriodoTempo() {
        diasSemana = new ArrayList<>();
    }

    public PeriodoTempo(Calendar horaInicio, Calendar horaFim, Calendar dataInicio, Calendar dataFim, List<DayOfWeek> diasSemana) {
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.diasSemana = diasSemana;
    }

    public String getHoraInicio() {
        return TIME_FORMATTER.format(horaInicio.getTime());
    }

    public void setHoraInicio(String horaInicio) {
        try {
            if (this.horaInicio == null) {
                this.horaInicio = Calendar.getInstance();
            }
            this.horaInicio.setTime(TIME_FORMATTER.parse(horaInicio));
        } catch (ParseException e) {
            Log.i(getClass().getCanonicalName(), e.getMessage());
        }
    }

    public String getHoraFim() {
        return TIME_FORMATTER.format(horaFim.getTime());
    }

    public void setHoraFim(String horaFim) {
        try {
            if (this.horaFim == null) {
                this.horaFim = Calendar.getInstance();
            }
            this.horaFim.setTime(TIME_FORMATTER.parse(horaFim));
        } catch (ParseException e) {
            Log.i(getClass().getCanonicalName(), e.getMessage());
        }
    }

    public void setDataInicio(String dataInicio) {
        if(TextUtils.isEmpty(dataInicio)) {
            this.dataInicio = null;
        } else {
            try {
                if (this.dataInicio == null) {
                    this.dataInicio = Calendar.getInstance();
                }
                this.dataInicio.setTime(DATE_FORMATTER.parse(dataInicio));
            } catch (ParseException e) {
                Log.i(getClass().getCanonicalName(), e.getMessage());
            }
        }
    }

    public String getDataInicio() {
        if(dataInicio == null) {
            return null;
        } else {
            return DATE_FORMATTER.format(dataInicio.getTime());
        }
    }

    public void setDataFim(String dataFim) {
        if(TextUtils.isEmpty(dataFim)) {
            this.dataFim = null;
        } else {
            try {
                if (this.dataFim == null) {
                    this.dataFim = Calendar.getInstance();
                }
                this.dataFim.setTime(DATE_FORMATTER.parse(dataFim));
            } catch (ParseException e) {
                Log.i(getClass().getCanonicalName(), e.getMessage());
            }
        }
    }

    public String getDataFim() {
        if(dataFim == null) {
            return null;
        } else {
            return DATE_FORMATTER.format(dataFim.getTime());
        }
    }

    public List<DayOfWeek> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<DayOfWeek> diasSemana) {
        this.diasSemana = diasSemana;
    }


    @Exclude
    public Calendar getHoraInicioCalendar() {
        return horaInicio;
    }

    @Exclude
    public void setHoraInicio(Calendar horaInicio) {
        this.horaInicio = horaInicio;
    }

    @Exclude
    public Calendar getHoraFimCalendar() {
        return horaFim;
    }

    @Exclude
    public void setHoraFim(Calendar horaFim) {
        this.horaFim = horaFim;
    }

    @Exclude
    public Calendar getDataInicioCalendar() {
        return dataInicio;
    }

    @Exclude
    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    @Exclude
    public Calendar getDataFimCalendar() {
        return dataFim;
    }

    @Exclude
    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < diasSemana.size(); index++) {
            stringBuilder.append(diasSemana.get(index).getDisplayName());
            if (index < diasSemana.size() - 1) {
                stringBuilder.append(" - ");
            }
        }
        if (!stringBuilder.toString().isEmpty()) {
            stringBuilder.append(", d");
        } else {
            stringBuilder.append("D");
        }
        stringBuilder.append("as ");
        stringBuilder.append(TIME_FORMATTER.format(getHoraInicioCalendar().getTime()));
        stringBuilder.append(" às ");
        stringBuilder.append(TIME_FORMATTER.format(getHoraFimCalendar().getTime()));

        //TODO: Adiciona a data de início e fim à string.

        return stringBuilder.toString();
    }
}
