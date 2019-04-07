package io.eventfriends.di.components;

import android.content.Context;

import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.Component;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.di.modules.app.AppModule;
import io.eventfriends.di.modules.app.AuthModule;
import io.eventfriends.di.modules.app.ContextModule;
import io.eventfriends.di.qualifiers.ApplicationContext;
import io.eventfriends.domain.interactors.AuthInteractor;
import io.eventfriends.domain.interactors.LoadEventsInteractor;

@Component(modules = {
        ContextModule.class,
        AuthModule.class,
        AppModule.class})
@Singleton
public interface AppComponent {

    AuthInteractor getAuthInteractor();

    LoadEventsInteractor getLoadEventsInteractor();

    @ApplicationContext
    Context getContext();

    ExecutorService getExecutorService();

    void injectApp(EventFriendsApp eventFriendsApp);

}
