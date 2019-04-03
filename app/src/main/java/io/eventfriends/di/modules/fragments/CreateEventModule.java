package io.eventfriends.di.modules.fragments;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.di.scopes.CreateEventScope;
import io.eventfriends.di.scopes.EventListScope;
import io.eventfriends.domain.AuthInteractor;
import io.eventfriends.presentation.createEvent.CreateEventPresenter;
import io.eventfriends.presentation.eventList.EventListPresenter;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class CreateEventModule {

    @CreateEventScope
    @Provides
    public CompositeDisposable getCompositeDisposable(){
        return new CompositeDisposable();
    }

    @CreateEventScope
    @Provides
    public CreateEventPresenter getEventListPresenter(AuthInteractor authInteractor,
                                                                CompositeDisposable compositeDisposable) {
        return new CreateEventPresenter(authInteractor, compositeDisposable);
    }
}
