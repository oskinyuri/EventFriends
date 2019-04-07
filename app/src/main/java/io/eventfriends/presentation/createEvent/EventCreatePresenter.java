package io.eventfriends.presentation.createEvent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import io.eventfriends.domain.interactors.AuthInteractor;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.User;
import io.eventfriends.domain.interactors.LoadEventsInteractor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EventCreatePresenter {
    private EventCreateView mView;

    private FirebaseAuth mFirebaseAuth;

    private FirebaseUser mFirebaseUser;
    private AuthInteractor mAuthInteractor;
    private CompositeDisposable mCompositeDisposable;

    private LoadEventsInteractor mLoadEventsInteractor;

    private Event mEvent;

    public EventCreatePresenter(AuthInteractor authInteractor,
                                CompositeDisposable compositeDisposable,
                                LoadEventsInteractor loadEventsInteractor) {

        mAuthInteractor = authInteractor;
        mCompositeDisposable = compositeDisposable;
        mLoadEventsInteractor = loadEventsInteractor;
    }

    public void onAttach(EventCreateView view) {
        mView = view;
        getFirebaseAuth();
    }

    public void onDetach() {
        mView = null;
        mCompositeDisposable.clear();
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

    public void pushEvent(Event event) {

        mEvent = event;

        // Set info about user
        mEvent.setUserId(mFirebaseUser.getUid());
        mEvent.setUserName(mFirebaseUser.getDisplayName());
        mEvent.setUserPhotoUrl(mFirebaseUser.getPhotoUrl() != null ? mFirebaseUser.getPhotoUrl().toString() : User.DEFAULT_PROFLIE_IMG);

        // Set timestamp
        mEvent.setTimestamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));

        mCompositeDisposable.add(mLoadEventsInteractor.getCurrentEventLoadState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loadState -> {
            switch (loadState.getStatus()) {
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
        }));

        mCompositeDisposable.add(mLoadEventsInteractor.pushEvent(mEvent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( key -> {
                    if (key != null && mView != null)
                        mView.startPageEventFragment(key);
                }));


    }
}
