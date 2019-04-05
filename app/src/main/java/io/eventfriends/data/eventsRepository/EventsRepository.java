package io.eventfriends.data.eventsRepository;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsLocalDataSource;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsRemoteDataFactory;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.repositories.IEventsRepository;
import io.eventfriends.util.ConnectionModel;
import io.eventfriends.util.ConnectionObservable;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

@SuppressWarnings("unchecked")
public class EventsRepository implements IEventsRepository {

    private EventsRemoteDataFactory mEventsRemoteDataFactory;
    private PagedList.Config mPagedListConfig;
    private EventsLocalDataSource mEventsLocalDataSource;
    private ConnectionObservable mConnectionObservable;

    //Test
    private BehaviorSubject<PagedList<Event>> subject;
    final private MediatorLiveData liveDataMerger;

    LiveData<PagedList<Event>> databaseSource;
    LiveData<PagedList<Event>> remoteSource;

    public EventsRepository(PagedList.Config pagedListConfig,
                            EventsRemoteDataFactory eventsRemoteDataFactory,
                            EventsLocalDataSource eventsLocalDataSource) {

        mPagedListConfig = pagedListConfig;
        mEventsRemoteDataFactory = eventsRemoteDataFactory;
        mEventsLocalDataSource = eventsLocalDataSource;

        liveDataMerger = new MediatorLiveData<>();

        databaseSource = new LivePagedListBuilder(mEventsLocalDataSource.getEvents(), mPagedListConfig)
                .build();

        remoteSource = new LivePagedListBuilder(mEventsRemoteDataFactory, mPagedListConfig)
                .build();

        //updateEventsList();
    }

    @Override
    public LiveData<PagedList<Event>> getEvents() {
        return liveDataMerger;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Observable<PagedList<Event>> getEventList() {
        return subject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<PagedList<Event>> getEventListFromBD() {
        return new RxPagedListBuilder(mEventsLocalDataSource.getEvents(), mPagedListConfig)
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {
                        Log.d("onItemAtEndLoaded", "onItemAtEndLoaded");
                    }
                })
                .buildObservable();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<PagedList<Event>> getEventListFromWeb() {
        return new RxPagedListBuilder(mEventsRemoteDataFactory, mPagedListConfig)
                .buildObservable();
    }

    @Override
    public Completable updateEventsList() {
        if (isOnline()) {
            liveDataMerger.removeSource(remoteSource);
            liveDataMerger.addSource(remoteSource, liveDataMerger::setValue);
        } else {
            liveDataMerger.removeSource(databaseSource);
            liveDataMerger.addSource(databaseSource, liveDataMerger::setValue);
        }
        return Completable.complete();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}
