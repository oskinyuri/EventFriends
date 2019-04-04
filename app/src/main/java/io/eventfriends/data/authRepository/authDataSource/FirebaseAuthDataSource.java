package io.eventfriends.data.authRepository.authDataSource;

import android.content.Context;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import io.eventfriends.data.authRepository.IAuthDataSource;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FirebaseAuthDataSource implements IAuthDataSource {

    private static final int REQUEST_AUTH_UI_CODE = 194;

    private Context mContext;

    public FirebaseAuthDataSource(Context context){
        mContext = context;
    }

    @Override
    public Single<FirebaseAuth> getFirebaseAuthInstance() {
        return Single.create(emitter -> emitter.onSuccess(FirebaseAuth.getInstance()));
    }

    @Override
    public Single<AuthUI> getAuthUIInstance() {
        return Single.create(emitter -> emitter.onSuccess(AuthUI.getInstance()));
    }

    @Override
    public Single<List<AuthUI.IdpConfig>> getAuthProviders() {
        return Single.create(emitter -> {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());
            emitter.onSuccess(providers);
        });
    }

    @Override
    public Single<Integer> getRequestCode() {
        return Single.create(emitter -> emitter.onSuccess(REQUEST_AUTH_UI_CODE));
    }

    @Override
    public Completable signOut() {
        return Completable.create(emitter -> AuthUI
                .getInstance()
                .signOut(mContext)
                .addOnCompleteListener((task) -> emitter.onComplete()));
    }
}
