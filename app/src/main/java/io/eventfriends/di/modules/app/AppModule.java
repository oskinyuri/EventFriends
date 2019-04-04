package io.eventfriends.di.modules.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Singleton
    @Provides
    public ExecutorService getExecutorService(){
        return Executors.newCachedThreadPool();
    }
}
