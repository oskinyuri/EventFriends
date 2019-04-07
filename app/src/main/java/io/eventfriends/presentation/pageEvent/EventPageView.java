package io.eventfriends.presentation.pageEvent;

import io.eventfriends.domain.entity.Event;

public interface EventPageView {

    void showProgress();

    void showToast(String msg);

    void hideProgress();

    void setEvent(Event event);
}
