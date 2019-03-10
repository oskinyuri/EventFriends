package io.eventfriends.di.components;

import javax.inject.Singleton;

import dagger.Component;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.di.modules.app.AuthModule;
import io.eventfriends.di.modules.app.ContextModule;
import io.eventfriends.domain.AuthInteractor;

@Component(modules = {
        ContextModule.class,
        AuthModule.class})
@Singleton
public interface AppComponent {

    AuthInteractor getAuthInteractor();

    void injectApp(EventFriendsApp eventFriendsApp);

}
