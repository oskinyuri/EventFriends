package io.eventfriends.presentation.eventList;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.eventfriends.domain.interactors.AuthInteractor;
import io.eventfriends.domain.interactors.LoadEventsInteractor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EventListPresenter {

    private EventListView mView;

    private FirebaseAuth mFirebaseAuth;

    private FirebaseUser mFirebaseUser;
    private AuthInteractor mAuthInteractor;
    private CompositeDisposable mCompositeDisposable;
    private LoadEventsInteractor mEventsInteractor;

    public EventListPresenter(AuthInteractor authInteractor,
                              CompositeDisposable compositeDisposable,
                              LoadEventsInteractor loadEventsInteractor
    ) {

        mAuthInteractor = authInteractor;
        mCompositeDisposable = compositeDisposable;
        mEventsInteractor = loadEventsInteractor;
    }

    public void onAttach(EventListView view) {
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

    public void loadEvents() {
        mView.showProgress();
        mCompositeDisposable.add(mEventsInteractor.getEventsPagedListFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventList -> {
                    mView.updateList(eventList);
                }));

        Log.d("sdf","sdfsdf");





        /*mCompositeDisposable.add(mEventsInteractor.getEventsPagedList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::updateList));*/
    }

    public void loadEventsFormWeb(){
        mCompositeDisposable.add(mEventsInteractor.getEventsPagedListFromWeb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventList -> {
                    /*if (eventList.size() == 0){
                    } else*/
                    Log.d("new data", "new data");
                    mView.updateList(eventList);
                }));
    }

}
