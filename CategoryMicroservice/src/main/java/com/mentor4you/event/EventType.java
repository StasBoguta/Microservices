package com.mentor4you.event;

public enum EventType {

    USER_CREATE("USER_CREATE"),
    USER_UPDATE("USER_UPDATE");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
