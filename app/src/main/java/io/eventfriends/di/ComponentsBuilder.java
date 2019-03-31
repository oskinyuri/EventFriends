package io.eventfriends.di;

import android.content.Context;

import io.eventfriends.di.components.AppComponent;
import io.eventfriends.di.components.DaggerAppComponent;
import io.eventfriends.di.components.DaggerMainComponent;
import io.eventfriends.di.components.DaggerSplashComponent;
import io.eventfriends.di.components.MainComponent;
import io.eventfriends.di.components.SplashComponent;
import io.eventfriends.di.modules.app.ContextModule;

public class ComponentsBuilder {

    private Context mContext;

    public ComponentsBuilder(Context context){
        mContext = context;
    }

    public AppComponent getAppComponent(){
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(mContext))
                .build();
    }

    public SplashComponent getSplashComponent(){
        return DaggerSplashComponent.builder()
                .appComponent(getAppComponent())
                .build();

    }

    public MainComponent getMainComponent(){
        return DaggerMainComponent.builder()
                .appComponent(getAppComponent())
                .build();
    }


}
