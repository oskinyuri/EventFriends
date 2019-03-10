package io.eventfriends.di.modules.app;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.di.qualifiers.ApplicationContext;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    @ApplicationContext
    public Context context(){
        return context.getApplicationContext();
    }

}
