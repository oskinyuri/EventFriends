package io.eventfriends.presentation.eventList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.eventfriends.domain.AuthInteractor;
import io.eventfriends.presentation.main.MainView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EventListPresenter {

    private EventListView mView;

    private FirebaseAuth mFirebaseAuth;

    private FirebaseUser mFirebaseUser;
    private AuthInteractor mAuthInteractor;
    private CompositeDisposable mCompositeDisposable;

    public EventListPresenter(AuthInteractor authInteractor,
                              CompositeDisposable compositeDisposable) {

        mAuthInteractor = authInteractor;
        mCompositeDisposable = compositeDisposable;
    }

    public void onAtach(EventListView view){
        mView = view;
        getFirebaseAuth();
    }

    public void onDetach() {
        mView = null;
        mCompositeDisposable.clear();
    }

    public void signOut() {
        mCompositeDisposable.add(mAuthInteractor.signOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUI));
    }

    private void updateUI() {
        mView.startSplashScreen();
    }

    private void getFirebaseAuth() {
        mCompositeDisposable.add(mAuthInteractor.getFirebaseAuthInstance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firebaseAuth -> {
                    mFirebaseAuth = firebaseAuth;
                    mFirebaseUser = mFirebaseAuth.getCurrentUser();
                }));
    }

}
