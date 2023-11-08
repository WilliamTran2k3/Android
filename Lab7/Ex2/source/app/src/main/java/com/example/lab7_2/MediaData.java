package com.example.lab7_2;

import android.net.Uri;

import androidx.annotation.NonNull;

public class MediaData {
    private String path;
    private String name;
    private String date;
    private Boolean checked;
    private Boolean isVideo;
    public MediaData(String uri, String name, String date, Boolean isVideo, Boolean checked){
        this.path = uri;
        this.name = name;
        this.date = date;
        this.checked = checked;
        this.isVideo = isVideo;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public Boolean getChecked() {
        return checked;
    }

    public Boolean getVideo() {
        return isVideo;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public void setVideo(Boolean video) {
        isVideo = video;
    }

    @NonNull
    @Override
    public String toString() {
        return "Media(" + this.path + "," + this.name + "," + this.date +  "," + this.isVideo + "," + this.checked + ")";
    }
}
