package com.example.bai2;

public class EventManage {
    private String name;
    private String place;
    private String datetime;
    private boolean enabled;

    public EventManage(String name, String place, String date, String time) {
        this.name = name;
        this.place = place;
        this.datetime = date + " " + time;
        this.enabled = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
