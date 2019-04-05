package io.eventfriends.di.modules.fragments;

import android.content.Context;

import java.util.concurrent.ExecutorService;

import androidx.paging.PagedList;
import dagger.Module;
import dagger.Provides;
import io.eventfriends.data.eventsRepository.EventsRepository;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsLocalDataSource;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsPositionDataSource;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsRemoteDataFactory;
import io.eventfriends.di.qualifiers.ApplicationContext;
import io.eventfriends.di.qualifiers.EventListContext;
import io.eventfriends.di.scopes.EventListScope;
import io.eventfriends.domain.interactors.AuthInteractor;
import io.eventfriends.domain.interactors.LoadEventsInteractor;
import io.eventfriends.domain.repositories.IEventsRepository;
import io.eventfriends.presentation.eventList.EventListPresenter;
import io.eventfriends.util.ConnectionObservable;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class EventListModule {

    Context mContext;

    public EventListModule(Context context){
        mContext = context;
    }

    @EventListScope
    @Provides
    public CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @EventListScope
    @Provides
    @EventListContext
    public Context getContext(){
        return mContext;
    }

    @EventListScope
    @Provides
    public ConnectionObservable getConnectionObservable(@EventListContext Context context){
        return new ConnectionObservable(context);
    }

    @EventListScope
    @Provides
    public EventListPresenter getEventListPresenter(AuthInteractor authInteractor,
                                                    CompositeDisposable compositeDisposable,
                                                    LoadEventsInteractor loadEventsInteractor,
                                                    @EventListContext Context context) {
        return new EventListPresenter(authInteractor, compositeDisposable, loadEventsInteractor, context);
    }

    @EventListScope
    @Provides
    public LoadEventsInteractor getEventInteractor(IEventsRepository eventsRepository) {
        return new LoadEventsInteractor(eventsRepository);
    }

    @EventListScope
    @Provides
    public IEventsRepository getIEventsRepository(PagedList.Config pagedListConfig,
                                                  EventsRemoteDataFactory eventsRemoteDataFactory,
                                                  EventsLocalDataSource eventsLocalDataSource) {
        return new EventsRepository(pagedListConfig, eventsRemoteDataFactory, eventsLocalDataSource);
    }

    @EventListScope
    @Provides
    public PagedList.Config getPagedListConfig() {
        return (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(15)
                .setPageSize(10)
                .build();
    }

    @EventListScope
    @Provides
    public EventsRemoteDataFactory getEventsRemoteDataFactory(EventsLocalDataSource eventsLocalDataSource, ExecutorService executorService){
        return new EventsRemoteDataFactory(eventsLocalDataSource, executorService);
    }

    @EventListScope
    @Provides
    public EventsPositionDataSource getEventsPositionDataSource(EventsLocalDataSource eventsLocalDataSource, ExecutorService executorService){
        return new EventsPositionDataSource(eventsLocalDataSource, executorService);
    }

    @EventListScope
    @Provides
    public EventsLocalDataSource getEventsLocalDataSource(@ApplicationContext Context context){
        return new EventsLocalDataSource(context);
    }
}
