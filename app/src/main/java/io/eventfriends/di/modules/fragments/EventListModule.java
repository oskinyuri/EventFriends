package io.eventfriends.di.modules.fragments;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.di.qualifiers.EventListContext;
import io.eventfriends.di.scopes.EventListScope;
import io.eventfriends.domain.interactors.AuthInteractor;
import io.eventfriends.domain.interactors.LoadEventsInteractor;
import io.eventfriends.presentation.eventList.EventListPresenter;
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
    public EventListPresenter getEventListPresenter(AuthInteractor authInteractor,
                                                    CompositeDisposable compositeDisposable,
                                                    LoadEventsInteractor loadEventsInteractor) {
        return new EventListPresenter(authInteractor, compositeDisposable, loadEventsInteractor);
    }


}
