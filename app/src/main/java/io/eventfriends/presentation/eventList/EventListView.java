package io.eventfriends.presentation.eventList;

import androidx.paging.PagedList;
import io.eventfriends.domain.entity.Event;

public interface EventListView {

    void updateList(PagedList<Event> eventList);

    void showProgress();

    void hideProgress();

    void startSplashScreen();

}
