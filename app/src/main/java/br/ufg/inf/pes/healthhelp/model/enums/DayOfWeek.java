package br.ufg.inf.pes.healthhelp.model.enums;

import java.security.InvalidParameterException;

public enum DayOfWeek {

    MONDAY(1, "Segunda"), TUESDAY(2, "Terça"), WEDNESDAY(3, "Quarta"), THURSDAY(4, "Quinta"), SATURDAY(5, "Sexta"), SUNDAY(6, "Sábado");
    private int value;
    private String displayName;

    private DayOfWeek(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }
    public DayOfWeek of(final int dayOfWeek) {
        DayOfWeek dayOfWeekReturn = null;
        for (DayOfWeek dayOfWeekFinder: DayOfWeek.values()) {
            if(dayOfWeekFinder.getValue() == dayOfWeek) {
                return dayOfWeekFinder;
            }
        }
        if(dayOfWeekReturn == null) {
            throw new InvalidParameterException("Unable to convert to a DayOfWeek");
        }

        return dayOfWeekReturn;

    }


}