package io.eventfriends.presentation.main;

import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.eventfriends.domain.AuthInteractor;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {

    private MainView mView;

    private AuthUI mAuthUI;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private AuthInteractor mAuthInteractor;

    private CompositeDisposable mCompositeDisposable;

    private Intent mAuthIntent;
    private int mAuthRequestCode;

    public MainPresenter(AuthInteractor authInteractor,
                         CompositeDisposable compositeDisposable) {

        mAuthInteractor = authInteractor;
        mCompositeDisposable = compositeDisposable;
    }


    public void onAttach(MainView view) {
        mView = view;

        // Start chain of observable
        getFirebaseAuth();
    }

    private void getFirebaseAuth() {
        mCompositeDisposable.add(mAuthInteractor.getFirebaseAuthInstance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firebaseAuth -> {
                    mFirebaseAuth = firebaseAuth;
                    getAuthUI();
                }));
    }

    private void getAuthUI() {
        mCompositeDisposable.add(mAuthInteractor.getAuthUIInstance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((authUI -> {
                    mAuthUI = authUI;
                    checkUserState();
                })));
    }

    private void checkUserState() {
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            if (mAuthUI != null)
                startSignInActivity();
        } else {
            updateUi();
        }
    }

    public void onDetach() {
        mView = null;
        mCompositeDisposable.clear();
    }

    public void signOut() {
        mCompositeDisposable.add(mAuthInteractor.signOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::startSignInActivity));

    }

    public void startSignInActivity() {
        mCompositeDisposable.add(createSignInIntent().startWith(getRequestCode()).subscribe(() -> {
            if (mView != null) {
                mView.startSignInActivity(mAuthIntent, mAuthRequestCode);
            }
        }));
    }

    private Completable createSignInIntent() {
        return Completable.create(emitter -> {
            mCompositeDisposable.add(mAuthInteractor.getAuthProviders()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(idpConfig -> {
                        mAuthIntent = mAuthUI.createSignInIntentBuilder()
                                .setAvailableProviders(idpConfig)
                                .build();
                        emitter.onComplete();
                    }));
        });
    }

    private Completable getRequestCode() {
        return Completable.create(emitter -> {
            mCompositeDisposable.add(mAuthInteractor.getRequestCode()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(requestCode -> {
                        mAuthRequestCode = requestCode;
                        emitter.onComplete();
                    }));
        });
    }

    public void updateUi(){
        if (mFirebaseUser == null) {

        } else {

        }
    }

}
