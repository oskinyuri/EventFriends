package io.eventfriends;

import android.app.Application;

import io.eventfriends.di.ComponentsBuilder;
import io.eventfriends.di.components.AppComponent;

public class EventFriendsApp extends Application {

    private ComponentsBuilder mComponentsBuilder;
    private AppComponent mAppComponent;

    private static EventFriendsApp sEventFriendsApp;

    public static EventFriendsApp getInstance(){
        return sEventFriendsApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sEventFriendsApp = EventFriendsApp.this;

        mComponentsBuilder = new ComponentsBuilder(this);
        mAppComponent = mComponentsBuilder.getAppComponent();
    }

    public ComponentsBuilder getComponentsBuilder(){
        return mComponentsBuilder;
    }


}
