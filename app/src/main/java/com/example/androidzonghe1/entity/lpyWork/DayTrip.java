package com.example.androidzonghe1.entity.lpyWork;

public class DayTrip {
    private String goOrCome;
    private String date;
    private String timeBegin;
    private String timeEnd;
    private String tripState;
    private String placeBegin;
    private String placeEnd;

    public DayTrip(String goOrCome, String date, String timeBegin, String timeEnd, String tripState, String placeBegin, String placeEnd) {
        this.goOrCome = goOrCome;
        this.date = date;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.tripState = tripState;
        this.placeBegin = placeBegin;
        this.placeEnd = placeEnd;
    }

    public DayTrip() {
    }

    public String getGoOrCome() {
        return goOrCome;
    }

    public void setGoOrCome(String goOrCome) {
        this.goOrCome = goOrCome;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTripState() {
        return tripState;
    }

    public void setTripState(String tripState) {
        this.tripState = tripState;
    }

    public String getPlaceBegin() {
        return placeBegin;
    }

    public void setPlaceBegin(String placeBegin) {
        this.placeBegin = placeBegin;
    }

    public String getPlaceEnd() {
        return placeEnd;
    }

    public void setPlaceEnd(String placeEnd) {
        this.placeEnd = placeEnd;
    }

    @Override
    public String toString() {
        return "DayTrip{" +
                "goOrCome='" + goOrCome + '\'' +
                ", date='" + date + '\'' +
                ", timeBegin='" + timeBegin + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", tripState='" + tripState + '\'' +
                ", placeBegin='" + placeBegin + '\'' +
                ", placeEnd='" + placeEnd + '\'' +
                '}';
    }
}
