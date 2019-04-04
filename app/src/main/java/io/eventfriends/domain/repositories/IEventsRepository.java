package io.eventfriends.domain.repositories;

import androidx.paging.PagedList;
import io.eventfriends.domain.entity.Event;
import io.reactivex.Observable;

public interface IEventsRepository {
    Observable<PagedList<Event>> getEventList();
    Observable<PagedList<Event>> getEventListFromBD();
    Observable<PagedList<Event>> getEventListFromWeb();
}
