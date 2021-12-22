package com.model;

public enum State {
    STARTED("STARTED"),
    FINISHED("FINISHED");

    private final String state;

    State(String state){
        this.state=state;
    }

    public String getState() {
        return state;
    }
}
