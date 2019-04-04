package io.eventfriends.domain.interactors;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.eventfriends.domain.repositories.IAuthRepository;
import io.reactivex.Completable;
import io.reactivex.Single;

public class AuthInteractor {

    private IAuthRepository mIAuthRepository;

    public AuthInteractor(IAuthRepository IAuthRepository){
        mIAuthRepository = IAuthRepository;
    }

    public Single<FirebaseAuth> getFirebaseAuthInstance(){
        return mIAuthRepository.getFirebaseAuthInstance();
    }

    public Single<AuthUI> getAuthUIInstance(){
        return mIAuthRepository.getAuthUIInstance();
    }

    public Single<List<AuthUI.IdpConfig>> getAuthProviders(){
        return mIAuthRepository.getAuthProviders();
    }

    public Single<Integer> getRequestCode(){
        return mIAuthRepository.getRequestCode();
    }

    public Completable signOut(){
        return mIAuthRepository.signOut();
    }

}
