package io.eventfriends.data.eventsRepository.eventsDataSource;

import java.util.concurrent.ExecutorService;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class EventsRemoteDataFactory extends DataSource.Factory{

    private EventsLocalDataSource mLocalDataSource;
    private ExecutorService mExecutorService;

    public EventsRemoteDataFactory(EventsLocalDataSource localDataSource,
                                   ExecutorService executorService) {
        mLocalDataSource = localDataSource;
        mExecutorService = executorService;
    }

    @NonNull
    @Override
    public DataSource create() {
        return new EventsRemoteDataSource(mLocalDataSource, mExecutorService);
        //return new EventsPositionDataSource(mLocalDataSource, mExecutorService);
    }
}
