package com.example.bai1;

public class Phone {
    private String name;
    private boolean checked;

    public Phone(String name) {
        this.name = name;
        this.checked = false;
    }

    public Phone(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
