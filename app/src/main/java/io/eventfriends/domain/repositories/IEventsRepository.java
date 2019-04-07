package io.eventfriends.domain.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.LoadState;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.subjects.BehaviorSubject;

public interface IEventsRepository {

    Completable updateEventsList();

    LiveData<PagedList<Event>> getEventsLiveData();

    MutableLiveData<LoadState> getEventsListLoadState();

    BehaviorSubject<Event> getEvent(String key);

    Maybe<String> pushEvent (Event event);

    BehaviorSubject<LoadState> getCurrentEventLoadState();

}
