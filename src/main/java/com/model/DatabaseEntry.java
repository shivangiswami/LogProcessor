package com.model;

public class DatabaseEntry {
    String id;
    Long duration;
    String type;
    String host;
    Boolean alert;

    public DatabaseEntry(String id, Long duration, String type, String host, Boolean alert) {
        this.id = id;
        this.duration = duration;
        this.type = type;
        this.host = host;
        this.alert = alert;
    }

    @Override
    public String toString() {
        return "DatabaseEntry{" +
                "id='" + id + '\'' +
                ", duration=" + duration +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", alert=" + alert +
                '}';
    }
}
