package io.eventfriends.di.modules.activities;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.di.scopes.MainScope;
import io.eventfriends.domain.AuthInteractor;
import io.eventfriends.presentation.main.MainPresenter;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class MainModule {

    @MainScope
    @Provides
    public CompositeDisposable getCompositeDisposable(){
        return new CompositeDisposable();
    }

    @MainScope
    @Provides
    public MainPresenter getMainPresenter(AuthInteractor authInteractor,
                                            CompositeDisposable compositeDisposable) {
        return new MainPresenter(authInteractor, compositeDisposable);
    }

}

