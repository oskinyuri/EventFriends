package io.eventfriends.domain.entity;

public class Event {

    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_PHOTO_URL = "userPhotoUrl";
    public static final String CREATE_EVENT_TIME = "createEventTime";

    public static final String DATE_EVENT = "dateEvent";
    public static final String TIME_EVENT = "timeEvent";
    public static final String LOCATION_EVENT = "locationEvent";
    public static final String EVENT_LINK = "eventLink";
    public static final String EVENT_TYPE = "eventType";
    public static final String FEEDBACK_LINK = "userFeedbackLink";
    public static final String ADDITIONAL_INFO = "additionalInfo";

    private String userId;
    private String userName;
    private String userPhotoUrl;
    private String createEventTime;

    private String dateEvent;
    private String timeEvent;
    private String locationEvent;
    private String eventLink;
    private String eventType;
    private String userFeedbackLink;
    private String additionalInfo;

    public Event() {
        //empty constructor for Firebase require
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

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getCreateEventTime() {
        return createEventTime;
    }

    public void setCreateEventTime(String createEventTime) {
        this.createEventTime = createEventTime;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    public String getLocationEvent() {
        return locationEvent;
    }

    public void setLocationEvent(String locationEvent) {
        this.locationEvent = locationEvent;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getUserFeedbackLink() {
        return userFeedbackLink;
    }

    public void setUserFeedbackLink(String userFeedbackLink) {
        this.userFeedbackLink = userFeedbackLink;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
