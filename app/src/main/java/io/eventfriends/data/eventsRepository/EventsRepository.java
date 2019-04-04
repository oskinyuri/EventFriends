package io.eventfriends.data.eventsRepository;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsLocalDataSource;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsRemoteDataFactory;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.repositories.IEventsRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EventsRepository implements IEventsRepository {

    private EventsRemoteDataFactory mEventsRemoteDataFactory;
    private PagedList.Config mPagedListConfig;
    private EventsLocalDataSource mEventsLocalDataSource;

    //Test
    final private MediatorLiveData liveDataMerger;

    public EventsRepository(PagedList.Config pagedListConfig,
                            EventsRemoteDataFactory eventsRemoteDataFactory,
                            EventsLocalDataSource eventsLocalDataSource) {

        mPagedListConfig = pagedListConfig;
        mEventsRemoteDataFactory = eventsRemoteDataFactory;
        mEventsLocalDataSource = eventsLocalDataSource;

        //Test
        liveDataMerger = new MediatorLiveData<>();
    }

    public LiveData<PagedList<Event>> getMovies(){
        return liveDataMerger;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<PagedList<Event>> getEventList() {
        return new RxPagedListBuilder(mEventsRemoteDataFactory, mPagedListConfig)
                .buildObservable().startWith(
                        //TODO pageSize и тут и еще в создании фабрики поменять на константу
                        new RxPagedListBuilder<>(mEventsLocalDataSource.getEvents(), 15)
                                .buildObservable());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<PagedList<Event>> getEventListFromBD() {
        return new RxPagedListBuilder(mEventsLocalDataSource.getEvents(), mPagedListConfig)
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {
                        Log.d("onItemAtEndLoaded","onItemAtEndLoaded");
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
}
