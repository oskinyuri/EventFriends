package io.eventfriends.di.modules.app;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.paging.PagedList;
import dagger.Module;
import dagger.Provides;
import io.eventfriends.data.eventsRepository.EventsRepository;
import io.eventfriends.data.eventsRepository.eventDataSource.CurrentEventDataSource;
import io.eventfriends.data.eventsRepository.eventsListDataSource.EventsLocalDataSource;
import io.eventfriends.data.eventsRepository.eventsListDataSource.EventsRemoteDataFactory;
import io.eventfriends.di.qualifiers.ApplicationContext;
import io.eventfriends.di.scopes.EventListScope;
import io.eventfriends.domain.interactors.LoadEventsInteractor;
import io.eventfriends.domain.repositories.IEventsRepository;

@Module
public class AppModule {

    @Singleton
    @Provides
    public ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    @Singleton
    @Provides
    public LoadEventsInteractor getEventInteractor(IEventsRepository eventsRepository) {
        return new LoadEventsInteractor(eventsRepository);
    }

    @Singleton
    @Provides
    public IEventsRepository getIEventsRepository(PagedList.Config pagedListConfig,
                                                  EventsRemoteDataFactory eventsRemoteDataFactory,
                                                  EventsLocalDataSource eventsLocalDataSource,
                                                  CurrentEventDataSource currentEventDataSource) {
        return new EventsRepository(
                pagedListConfig,
                eventsRemoteDataFactory,
                eventsLocalDataSource,
                currentEventDataSource);
    }

    @Singleton
    @Provides
    public CurrentEventDataSource getCurrentEventDataSource() {
        return new CurrentEventDataSource();
    }

    @Singleton
    @Provides
    public PagedList.Config getPagedListConfig() {
        return (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(10)
                .build();
    }

    @Singleton
    @Provides
    public EventsRemoteDataFactory getEventsRemoteDataFactory(EventsLocalDataSource eventsLocalDataSource, ExecutorService executorService) {
        return new EventsRemoteDataFactory(eventsLocalDataSource, executorService);
    }

    @Singleton
    @Provides
    public EventsLocalDataSource getEventsLocalDataSource(@ApplicationContext Context context) {
        return new EventsLocalDataSource(context);
    }
}
