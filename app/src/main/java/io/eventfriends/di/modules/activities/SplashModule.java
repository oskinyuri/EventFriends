package io.eventfriends.di.modules.activities;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.di.scopes.SplashScope;
import io.eventfriends.domain.AuthInteractor;
import io.eventfriends.presentation.splashActivity.SplashPresenter;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class SplashModule {

    @SplashScope
    @Provides
    public CompositeDisposable getCompositeDisposable(){
        return new CompositeDisposable();
    }

    @SplashScope
    @Provides
    public SplashPresenter getSplashPresenter(AuthInteractor authInteractor,
                                              CompositeDisposable compositeDisposable) {
        return new SplashPresenter(authInteractor, compositeDisposable);
    }

}
