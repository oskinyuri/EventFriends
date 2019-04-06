package io.eventfriends.data.authRepository;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.eventfriends.domain.repositories.IAuthRepository;
import io.reactivex.Completable;
import io.reactivex.Single;

public class AuthRepository implements IAuthRepository {

    private IAuthDataSource mAuthDataSource;

    public AuthRepository(IAuthDataSource dataSource){
        mAuthDataSource = dataSource;
    }

    @Override
    public Single<FirebaseAuth> getFirebaseAuthInstance() {
        return mAuthDataSource.getFirebaseAuthInstance();
    }

    @Override
    public Single<AuthUI> getAuthUIInstance() {
        return mAuthDataSource.getAuthUIInstance();
    }

    @Override
    public Single<List<AuthUI.IdpConfig>> getAuthProviders() {
        return mAuthDataSource.getAuthProviders();
    }

    @Override
    public Single<Integer> getRequestCode() {
        return mAuthDataSource.getRequestCode();
    }

    @Override
    public Completable signOut() {
        return mAuthDataSource.signOut();
    }
}
