package io.eventfriends.data.eventsRepository;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsLocalDataSource;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsRemoteDataFactory;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.LoadState;
import io.eventfriends.domain.repositories.IEventsRepository;
import io.reactivex.Completable;

@SuppressWarnings("unchecked")
public class EventsRepository implements IEventsRepository {

    private EventsRemoteDataFactory mEventsRemoteDataFactory;
    private PagedList.Config mPagedListConfig;
    private EventsLocalDataSource mEventsLocalDataSource;

    final private MediatorLiveData liveDataMerger;
    private MutableLiveData<LoadState> mLoadStatus;

    LiveData<PagedList<Event>> databaseSource;
    LiveData<PagedList<Event>> remoteSource;

    public EventsRepository(PagedList.Config pagedListConfig,
                            EventsRemoteDataFactory eventsRemoteDataFactory,
                            EventsLocalDataSource eventsLocalDataSource) {

        mPagedListConfig = pagedListConfig;
        mEventsRemoteDataFactory = eventsRemoteDataFactory;
        mEventsLocalDataSource = eventsLocalDataSource;

        mLoadStatus = mEventsRemoteDataFactory.getNetworkStatus();

        liveDataMerger = new MediatorLiveData<>();

        databaseSource = new LivePagedListBuilder(mEventsLocalDataSource.getEvents(), mPagedListConfig)
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        mLoadStatus.setValue(LoadState.FAILED);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                        mLoadStatus.setValue(LoadState.FAILED);
                    }
                })
                .build();

        remoteSource = new LivePagedListBuilder(mEventsRemoteDataFactory, mPagedListConfig)
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        mLoadStatus.setValue(LoadState.FAILED);
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
        if (isOnline()) {
            liveDataMerger.removeSource(remoteSource);
            remoteSource = new LivePagedListBuilder(mEventsRemoteDataFactory, mPagedListConfig)
                    .setBoundaryCallback(new PagedList.BoundaryCallback() {
                        @Override
                        public void onZeroItemsLoaded() {
                            mLoadStatus.setValue(LoadState.FAILED);
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
                mLoadStatus.setValue(LoadState.FAILED);
            }
        }
        return Completable.complete();
    }

    @Override
    public MutableLiveData<LoadState> getLoadStatus() {
        return mLoadStatus;
    }

    private boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
