package io.eventfriends.data.eventsRepository.eventsListDataSource;

import java.util.concurrent.ExecutorService;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.eventfriends.domain.entity.LoadState;

public class EventsRemoteDataFactory extends DataSource.Factory{

    private EventsLocalDataSource mLocalDataSource;
    private ExecutorService mExecutorService;
    private MutableLiveData<LoadState> networkStatus;

    private EventsRemoteDataSource mEventsRemoteDataSource;

    public EventsRemoteDataFactory(EventsLocalDataSource localDataSource,
                                   ExecutorService executorService) {
        mLocalDataSource = localDataSource;
        mExecutorService = executorService;

        //TODO может быть пихнуть в Dagger
        mEventsRemoteDataSource = new EventsRemoteDataSource(mLocalDataSource, mExecutorService);
        networkStatus = mEventsRemoteDataSource.getLoadStatus();
    }

    @NonNull
    @Override
    public DataSource create() {
        networkStatus.postValue(LoadState.LOADING);
        return mEventsRemoteDataSource;
    }

    public MutableLiveData<LoadState> getNetworkStatus(){
        return networkStatus;
    }
}
