package io.eventfriends.di.modules.app;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.data.AuthRepository.AuthDataSource.FirebaseAuthDataSource;
import io.eventfriends.data.AuthRepository.AuthRepository;
import io.eventfriends.data.AuthRepository.IAuthDataSource;
import io.eventfriends.di.qualifiers.ApplicationContext;
import io.eventfriends.domain.AuthInteractor;
import io.eventfriends.domain.IAuthRepository;

@Module
public class AuthModule {

    @Singleton
    @Provides
    public AuthInteractor getAuthInteractor(IAuthRepository iAuthRepository){
        return new AuthInteractor(iAuthRepository);
    }

    @Singleton
    @Provides
    public IAuthRepository getAuthRepository(IAuthDataSource dataSource){
        return new AuthRepository(dataSource);
    }

    @Singleton
    @Provides
    public IAuthDataSource getIAuthDataSource(@ApplicationContext Context context){
        return new FirebaseAuthDataSource(context);
    }


}
