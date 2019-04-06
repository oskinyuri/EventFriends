package io.eventfriends.presentation.eventList;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.LoadState;
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

    private MutableLiveData<LoadState> mLoadStatusLiveData;
    private Observer<LoadState> mLoadStateObserver;

    private LiveData<PagedList<Event>> mEventsListLiveData;
    private Observer<PagedList<Event>> mEventsObserver;

    public EventListPresenter(AuthInteractor authInteractor,
                              CompositeDisposable compositeDisposable,
                              LoadEventsInteractor loadEventsInteractor
    ) {

        mAuthInteractor = authInteractor;
        mCompositeDisposable = compositeDisposable;
        mEventsInteractor = loadEventsInteractor;

        mLoadStatusLiveData = mEventsInteractor.getLoadStatus();
        mLoadStateObserver = loadState -> {
            switch (loadState.getStatus()){
                case SUCCESS:
                    mView.hideProgress();
                    break;
                case RUNNING:
                    mView.showProgress();
                    break;
                case FAILED:
                    mView.hideProgress();
                    mView.showToast(loadState.getMsg());
                    break;
            }
        };

        mEventsListLiveData = mEventsInteractor.getEvents();
        mEventsObserver = eventList -> mView.updateList(eventList);
    }

    public void onAttach(EventListView view) {
        mView = view;
        getFirebaseAuth();
        mLoadStatusLiveData.observeForever(mLoadStateObserver);
        mEventsListLiveData.observeForever(mEventsObserver);
    }

    public void onDetach() {
        mView = null;
        mCompositeDisposable.clear();
        mLoadStatusLiveData.removeObserver(mLoadStateObserver);
        mEventsListLiveData.removeObserver(mEventsObserver);
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

    public void updateEventsList(){
        mEventsInteractor.updateEventsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
