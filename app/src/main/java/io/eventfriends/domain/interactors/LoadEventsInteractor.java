package io.eventfriends.domain.interactors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.LoadState;
import io.eventfriends.domain.repositories.IEventsRepository;
import io.reactivex.Completable;

public class LoadEventsInteractor {

    private IEventsRepository mEventsRepository;

    public LoadEventsInteractor(IEventsRepository eventsRepository) {
        mEventsRepository = eventsRepository;
    }

    public LiveData<PagedList<Event>> getEvents(){
        return mEventsRepository.getEventsLiveData();
    }

    public Completable updateEventsList(){
        return mEventsRepository.updateEventsList();
    }

    public MutableLiveData<LoadState> getLoadStatus(){
        return mEventsRepository.getLoadStatus();
    }
}
