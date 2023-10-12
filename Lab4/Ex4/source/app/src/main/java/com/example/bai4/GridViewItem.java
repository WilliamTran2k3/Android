package com.example.bai4;

public class GridViewItem {
    private boolean on;
    private String string;

    public GridViewItem(String string) {
        this.string = string;
        this.on = false;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
