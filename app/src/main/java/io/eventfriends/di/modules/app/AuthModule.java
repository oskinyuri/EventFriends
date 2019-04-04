package io.eventfriends.di.modules.app;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.eventfriends.data.authRepository.authDataSource.FirebaseAuthDataSource;
import io.eventfriends.data.authRepository.AuthRepository;
import io.eventfriends.data.authRepository.IAuthDataSource;
import io.eventfriends.di.qualifiers.ApplicationContext;
import io.eventfriends.domain.interactors.AuthInteractor;
import io.eventfriends.domain.repositories.IAuthRepository;

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
