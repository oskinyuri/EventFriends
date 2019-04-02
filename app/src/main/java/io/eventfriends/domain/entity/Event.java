package io.eventfriends.domain.entity;

public class Event {
    private String data;
    private String userId;
    private String userName;
    private String eventType;

    public Event(){

    }

    public Event(String data, String userId, String userName, String eventType) {
        this.data = data;
        this.userId = userId;
        this.userName = userName;
        this.eventType = eventType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
