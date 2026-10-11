package com.jwalanavdurga.dto;

public class TodayFestivalResponse {

    private FestivalResponse festival;
    private FestivalDayResponse day;

    public FestivalResponse getFestival() {
        return festival;
    }

    public void setFestival(FestivalResponse festival) {
        this.festival = festival;
    }

    public FestivalDayResponse getDay() {
        return day;
    }

    public void setDay(FestivalDayResponse day) {
        this.day = day;
    }
}