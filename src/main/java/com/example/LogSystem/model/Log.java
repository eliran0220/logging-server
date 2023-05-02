package com.example.LogSystem.model;

public class Log {
    private String description;
    private String date;

    public Log(String description, String date) {
        this.description = description;
        this.date = date;
    }


    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
