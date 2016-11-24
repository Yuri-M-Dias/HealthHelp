package br.ufg.inf.pes.healthhelp.model;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Atendimento implements Serializable {
    public static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Exclude
    private String id;

    private Agenda agenda;
    private Paciente paciente;
    private Calendar horaInicio;
    private Calendar horaFim;

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Calendar getHoraInicioCalendar() {
        return horaInicio;
    }
    public String getHoraInicio() {
        return DATETIME_FORMATTER.format(horaInicio.getTime());
    }

    @Exclude
    public void setHoraInicio(Calendar horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        try {
            if (this.horaInicio == null) {
                this.horaInicio = Calendar.getInstance();
            }
            this.horaInicio.setTime(DATETIME_FORMATTER.parse(horaInicio));
        } catch (ParseException e) {
            Log.i(getClass().getCanonicalName(), e.getMessage());
        }
    }

    public Calendar getHoraFimCalendar() {
        return horaFim;
    }
    public String getHoraFim() {
        return DATETIME_FORMATTER.format(horaInicio.getTime());
    }

    @Exclude
    public void setHoraFim(Calendar horaFim) {
        this.horaFim = horaFim;
    }

    public void setHoraFim(String horaFim) {
        try {
            if (this.horaFim == null) {
                this.horaFim = Calendar.getInstance();
            }
            this.horaFim.setTime(DATETIME_FORMATTER.parse(horaFim));
        } catch (ParseException e) {
            Log.i(getClass().getCanonicalName(), e.getMessage());
        }
    }

    public boolean mesmaDataInicio(Calendar data) {
        return data.get(Calendar.YEAR) == horaInicio.get(Calendar.YEAR)
            && data.get(Calendar.MONTH) == horaInicio.get(Calendar.MONTH)
            && data.get(Calendar.DAY_OF_MONTH) == horaInicio.get(Calendar.DAY_OF_MONTH);
    }

    public boolean mesmaDataFim(Calendar data) {
        return data.get(Calendar.YEAR) == horaFim.get(Calendar.YEAR)
            && data.get(Calendar.MONTH) == horaFim.get(Calendar.MONTH)
            && data.get(Calendar.DAY_OF_MONTH) == horaFim.get(Calendar.DAY_OF_MONTH);
    }
}
