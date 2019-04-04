package io.eventfriends.domain.interactors;

import androidx.paging.PagedList;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.repositories.IEventsRepository;
import io.reactivex.Observable;

public class LoadEventsInteractor {

    private IEventsRepository mEventsRepository;

    public LoadEventsInteractor(IEventsRepository eventsRepository) {
        mEventsRepository = eventsRepository;
    }

    public Observable<PagedList<Event>> getEventsPagedList(){
        return mEventsRepository.getEventList();
    }

    public Observable<PagedList<Event>> getEventsPagedListFromDB(){
        return mEventsRepository.getEventListFromBD();
    }

    public Observable<PagedList<Event>> getEventsPagedListFromWeb(){
        return mEventsRepository.getEventListFromWeb();
    }
}
