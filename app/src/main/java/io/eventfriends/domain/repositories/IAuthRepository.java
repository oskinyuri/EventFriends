package io.eventfriends.domain.repositories;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IAuthRepository {

    Single<FirebaseAuth> getFirebaseAuthInstance();

    Single<AuthUI> getAuthUIInstance();

    Single<List<AuthUI.IdpConfig>> getAuthProviders();

    Single<Integer> getRequestCode();

    Completable signOut();
}
