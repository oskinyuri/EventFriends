package io.eventfriends.di.modules.fragments;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.di.scopes.EventPageScope;
import io.eventfriends.domain.interactors.LoadEventsInteractor;
import io.eventfriends.presentation.pageEvent.EventPagePresenter;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class EventPageModule {

    @EventPageScope
    @Provides
    public CompositeDisposable getCompositeDisposable(){
        return new CompositeDisposable();
    }

    @EventPageScope
    @Provides
    public EventPagePresenter getEventPagePresenter(CompositeDisposable compositeDisposable,
                                                    LoadEventsInteractor loadEventsInteractor) {
        return new EventPagePresenter(compositeDisposable, loadEventsInteractor);
    }

}
