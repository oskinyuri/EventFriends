package io.eventfriends.domain.entity;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {

    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_PHOTO_URL = "userPhotoUrl";
    public static final String TIMESTAMP = "timestamp";

    public static final String TITLE_EVENT = "title_event";
    public static final String DATE_EVENT = "dateEvent";
    public static final String TIME_EVENT = "timeEvent";
    public static final String LOCATION_EVENT = "locationEvent";
    public static final String EVENT_LINK = "eventLink";
    public static final String EVENT_TYPE = "eventType";
    public static final String FEEDBACK_LINK = "userFeedbackLink";
    public static final String ADDITIONAL_INFO = "additionalInfo";
    public static final String UNIQUE_ID = "uniqueId";

    @PrimaryKey
    @NonNull
    private String uniqueId;

    //id event
    public String userId;

    // Info about user
    private String userName;
    private String userPhotoUrl;

    // Time when event was created
    private String timestamp;

    //Info about event from create form
    private String titleEvent;
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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String createEventTime) {
        this.timestamp = createEventTime;
    }

    public String getTitleEvent() {
        return titleEvent;
    }

    public void setTitleEvent(String titleEvent) {
        this.titleEvent = titleEvent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Objects.equals(uniqueId, event.uniqueId) &&
                Objects.equals(userId, event.userId) &&
                Objects.equals(userName, event.userName) &&
                Objects.equals(userPhotoUrl, event.userPhotoUrl) &&
                Objects.equals(timestamp, event.timestamp) &&
                Objects.equals(dateEvent, event.dateEvent) &&
                Objects.equals(timeEvent, event.timeEvent) &&
                Objects.equals(locationEvent, event.locationEvent) &&
                Objects.equals(eventLink, event.eventLink) &&
                Objects.equals(eventType, event.eventType) &&
                Objects.equals(userFeedbackLink, event.userFeedbackLink) &&
                Objects.equals(additionalInfo, event.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                uniqueId,
                userId,
                userName,
                userPhotoUrl,
                timestamp,
                dateEvent,
                timeEvent,
                locationEvent,
                eventLink,
                eventType,
                userFeedbackLink,
                additionalInfo);
    }

    public static DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getUniqueId().equals(newItem.getUniqueId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.equals(newItem);
        }
    };
}
