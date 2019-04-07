package io.eventfriends.di.modules.fragments;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.di.scopes.EventCreateScope;
import io.eventfriends.domain.interactors.AuthInteractor;
import io.eventfriends.domain.interactors.LoadEventsInteractor;
import io.eventfriends.presentation.createEvent.EventCreatePresenter;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class EventCreateModule {

    @EventCreateScope
    @Provides
    public CompositeDisposable getCompositeDisposable(){
        return new CompositeDisposable();
    }

    @EventCreateScope
    @Provides
    public EventCreatePresenter getEventListPresenter(AuthInteractor authInteractor,
                                                      CompositeDisposable compositeDisposable,
                                                      LoadEventsInteractor loadEventsInteractor) {
        return new EventCreatePresenter(authInteractor, compositeDisposable, loadEventsInteractor);
    }
}
