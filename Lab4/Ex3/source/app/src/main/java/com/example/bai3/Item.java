package com.example.bai3;

public class Item {
    private String text;
    private boolean isCheck;

    public Item(String text) {
        this.text = text;
        this.isCheck = false;
    }

    public String getText() {
        return text;
    }
    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
    public boolean getCheck() {
        return isCheck;
    }
}