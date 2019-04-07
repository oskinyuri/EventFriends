package io.eventfriends.data.eventsRepository.eventDataSource;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.LoadState;
import io.eventfriends.util.UtilNetworkState;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CurrentEventDataSource {

    private BehaviorSubject<LoadState> mLoadState;
    private BehaviorSubject<Event> mEventSubject;
    private Event mEvent;

    public CurrentEventDataSource() {
        mLoadState = BehaviorSubject.create();
        mEventSubject = BehaviorSubject.create();
    }

    public BehaviorSubject<Event> getEvent(String key) {
        loadEvent(key).subscribeOn(Schedulers.io()).subscribe();
        return mEventSubject;
    }

    public Maybe<String> pushEvent(Event event) {
        return Maybe.fromCallable(() -> {

            if (UtilNetworkState.isOnline()) {
                mLoadState.onNext(LoadState.LOADING);

                //Получаем ссылку на расположение бд
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference eventsRef = database.getReference("Events");

                //Получаем уникальный ключ и сетим его в запись.
                String uniqueId = eventsRef.push().getKey();
                event.setUniqueId(uniqueId);

                if (uniqueId != null) {
                    eventsRef.child(uniqueId).setValue(event);
                    mLoadState.onNext(LoadState.LOADED);
                    return event.getUniqueId();
                } else {
                    mLoadState.onNext(LoadState.FAILED);
                    return null;
                }
            } else {
                mLoadState.onNext(LoadState.FAILED);
                return null;
            }
        });
    }

    private Completable loadEvent(String key) {
        return Completable.fromAction(() -> {

            if (UtilNetworkState.isOnline()) {
                //Получаем ссылку на расположение бд
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Events").child(key);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mEvent = dataSnapshot.getValue(Event.class);
                        mLoadState.onNext(LoadState.LOADED);
                        mEventSubject.onNext(mEvent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mLoadState.onNext(LoadState.FAILED);
                    }
                });
            } else {
                mLoadState.onNext(LoadState.FAILED);
            }
        });
    }

    public BehaviorSubject<LoadState> getCurrentEventLoadState() {
        return mLoadState;
    }
}
