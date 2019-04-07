package io.eventfriends.data.eventsRepository;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.eventfriends.data.eventsRepository.eventDataSource.CurrentEventDataSource;
import io.eventfriends.data.eventsRepository.eventsListDataSource.EventsLocalDataSource;
import io.eventfriends.data.eventsRepository.eventsListDataSource.EventsRemoteDataFactory;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.LoadState;
import io.eventfriends.domain.repositories.IEventsRepository;
import io.eventfriends.util.UtilNetworkState;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.subjects.BehaviorSubject;

@SuppressWarnings("unchecked")
public class EventsRepository implements IEventsRepository {

    private EventsRemoteDataFactory mEventsRemoteDataFactory;
    private PagedList.Config mPagedListConfig;
    private EventsLocalDataSource mEventsLocalDataSource;

    private CurrentEventDataSource mCurrentCurrentEventDataSource;
    private BehaviorSubject<LoadState> mCurrentEventLoadState;

    final private MediatorLiveData liveDataMerger;
    private MutableLiveData<LoadState> mEventsListLoadState;

    LiveData<PagedList<Event>> databaseSource;
    LiveData<PagedList<Event>> remoteSource;

    public EventsRepository(PagedList.Config pagedListConfig,
                            EventsRemoteDataFactory eventsRemoteDataFactory,
                            EventsLocalDataSource eventsLocalDataSource,
                            CurrentEventDataSource currentCurrentEventDataSource) {

        mPagedListConfig = pagedListConfig;
        mEventsRemoteDataFactory = eventsRemoteDataFactory;
        mEventsLocalDataSource = eventsLocalDataSource;
        mCurrentCurrentEventDataSource = currentCurrentEventDataSource;

        mEventsListLoadState = mEventsRemoteDataFactory.getNetworkStatus();
        mCurrentEventLoadState = mCurrentCurrentEventDataSource.getCurrentEventLoadState();

        liveDataMerger = new MediatorLiveData<>();

        databaseSource = new LivePagedListBuilder(mEventsLocalDataSource.getEvents(), mPagedListConfig)
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        mEventsListLoadState.setValue(LoadState.FAILED);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                        mEventsListLoadState.setValue(LoadState.FAILED);
                    }
                })
                .build();

        remoteSource = new LivePagedListBuilder(mEventsRemoteDataFactory, mPagedListConfig)
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        mEventsListLoadState.setValue(LoadState.FAILED);
                    }
                })
                .build();

        //Init load data in liveData
        updateEventsList();
    }

    @Override
    public LiveData<PagedList<Event>> getEventsLiveData() {
        return liveDataMerger;
    }

    @Override
    public Completable updateEventsList() {
        if (UtilNetworkState.isOnline()) {
            liveDataMerger.removeSource(remoteSource);
            remoteSource = new LivePagedListBuilder(mEventsRemoteDataFactory, mPagedListConfig)
                    .setBoundaryCallback(new PagedList.BoundaryCallback() {
                        @Override
                        public void onZeroItemsLoaded() {
                            mEventsListLoadState.setValue(LoadState.FAILED);
                        }
                    })
                    .build();

            liveDataMerger.removeSource(databaseSource);
            liveDataMerger.addSource(remoteSource,

                    liveDataMerger::setValue);
        } else {
            if (!remoteSource.hasActiveObservers() & !databaseSource.hasActiveObservers()) {
                liveDataMerger.addSource(databaseSource, liveDataMerger::setValue);
            } else {
                mEventsListLoadState.setValue(LoadState.FAILED);
            }
        }
        return Completable.complete();
    }

    @Override
    public MutableLiveData<LoadState> getEventsListLoadState() {
        return mEventsListLoadState;
    }

    @Override
    public BehaviorSubject<Event> getEvent(String key) {
        return mCurrentCurrentEventDataSource.getEvent(key);
    }

    @Override
    public Maybe<String> pushEvent(Event event) {
        return mCurrentCurrentEventDataSource.pushEvent(event);
    }

    @Override
    public BehaviorSubject<LoadState> getCurrentEventLoadState() {
        return mCurrentEventLoadState;
    }
}
