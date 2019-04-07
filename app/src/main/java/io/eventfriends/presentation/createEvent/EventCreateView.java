package io.eventfriends.presentation.createEvent;

import io.eventfriends.domain.entity.Event;

public interface EventCreateView {

    void showProgress();

    void showToast(String msg);

    void hideProgress();

    void startPageEventFragment(String key);
}
