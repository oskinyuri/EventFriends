package io.eventfriends.domain.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.LoadState;
import io.reactivex.Completable;

public interface IEventsRepository {

    Completable updateEventsList();

    LiveData<PagedList<Event>> getEventsLiveData();

    MutableLiveData<LoadState> getLoadStatus();
}
