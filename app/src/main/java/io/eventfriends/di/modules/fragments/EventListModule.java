package io.eventfriends.di.modules.fragments;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.di.scopes.EventListScope;
import io.eventfriends.domain.AuthInteractor;
import io.eventfriends.presentation.eventList.EventListPresenter;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class EventListModule {

    @EventListScope
    @Provides
    public CompositeDisposable getCompositeDisposable(){
        return new CompositeDisposable();
    }

    @EventListScope
    @Provides
    public EventListPresenter getEventListPresenter(AuthInteractor authInteractor,
                                               CompositeDisposable compositeDisposable) {
        return new EventListPresenter(authInteractor, compositeDisposable);
    }
}
