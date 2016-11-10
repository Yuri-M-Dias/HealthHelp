package br.ufg.inf.pes.healthhelp.model;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.enums.DayOfWeek;

public class PeriodoTempo implements Serializable {
    private Calendar horaInicio;
    private Calendar horaFim;
    private Calendar dataInicio; // dateFormat no estilo dd-MM-yyyy
    private Calendar dataFim; // dateFormat no estilo dd-MM-yyyy

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

    public Calendar getHoraInicio() {
        return horaInicio;
    }

    @Exclude
    public void setHoraInicio(Calendar horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            if(this.horaInicio == null) {
                this.horaInicio = Calendar.getInstance();
            }
            this.horaInicio.setTime(simpleDateFormat.parse(horaInicio));
        } catch (ParseException e) {
            Log.i(getClass().getCanonicalName(), e.getMessage());
        }
    }

    public Calendar getHoraFim() {
        return horaFim;
    }

    public List<DayOfWeek> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<DayOfWeek> diasSemana) {
        this.diasSemana = diasSemana;
    }

    @Exclude
    public void setHoraFim(Calendar horaFim) {
        this.horaFim = horaFim;
    }

    public void setHoraFim(String horaFim) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            if(this.horaFim== null) {
                this.horaFim = Calendar.getInstance();
            }
            this.horaFim.setTime(simpleDateFormat.parse(horaFim));
        } catch (ParseException e) {
            Log.i(getClass().getCanonicalName(), e.getMessage());
        }
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }




}
