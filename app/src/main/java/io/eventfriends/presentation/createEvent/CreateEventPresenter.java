package io.eventfriends.presentation.createEvent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.eventfriends.domain.AuthInteractor;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.User;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CreateEventPresenter {
    private CreateEventView mView;

    private FirebaseAuth mFirebaseAuth;

    private FirebaseUser mFirebaseUser;
    private AuthInteractor mAuthInteractor;
    private CompositeDisposable mCompositeDisposable;

    private Event mEvent;

    public CreateEventPresenter(AuthInteractor authInteractor,
                              CompositeDisposable compositeDisposable) {

        mAuthInteractor = authInteractor;
        mCompositeDisposable = compositeDisposable;
    }

    public void onAttach(CreateEventView view){
        mView = view;
        getFirebaseAuth();
    }

    public void onDetach() {
        mView = null;
        mCompositeDisposable.clear();
    }

    private void updateUI() {
        //TODO если все ок вернутся на главны, или если будет время перейти на созданный евент, это
        //TODO nested navcomponent practice
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

    public void setEvent(Event event) {
        mEvent = event;

        mEvent.setUserId(mFirebaseUser.getUid());
        mEvent.setUserName(mFirebaseUser.getDisplayName());
        mEvent.setUserPhotoUrl(mFirebaseUser.getPhotoUrl() != null ? mFirebaseUser.getPhotoUrl().toString() : User.DEFAULT_PROFLIE_IMG);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsRef = database.getReference("Events");
        eventsRef.push().setValue(mEvent);
    }
}
