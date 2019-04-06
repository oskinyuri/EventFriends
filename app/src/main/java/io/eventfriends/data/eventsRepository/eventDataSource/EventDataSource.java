package io.eventfriends.data.eventsRepository.eventDataSource;

import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.LoadState;
import io.reactivex.subjects.BehaviorSubject;

public class EventDataSource {

    BehaviorSubject<LoadState> mLoadState;

    public EventDataSource(){
        mLoadState = BehaviorSubject.create();
    }

    public Event getEvent(String key){
        //TODO getEvent
        return null;
    }

    public void pushEvent(Event event){
        //TODO pushEvent
        mLoadState.onNext(LoadState.LOADING);
    }

    public BehaviorSubject<LoadState> getLoadState(){
        return mLoadState;
    }
}
