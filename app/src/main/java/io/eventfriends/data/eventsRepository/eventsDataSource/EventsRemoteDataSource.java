package io.eventfriends.data.eventsRepository.eventsDataSource;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import io.eventfriends.domain.entity.Event;

public class EventsRemoteDataSource extends ItemKeyedDataSource<String, Event> {

    private EventsLocalDataSource mLocalDataSource;
    private ExecutorService mExecutorService;

    public EventsRemoteDataSource(EventsLocalDataSource localDataSource,
                                  ExecutorService executorService) {
        mLocalDataSource = localDataSource;
        mExecutorService = executorService;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<Event> callback) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Events");
        Query newQuery = reference.orderByKey().limitToLast(params.requestedLoadSize);
        newQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Event> mTempData = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Event item = postSnapshot.getValue(Event.class);
                    mTempData.add(item);
                }

                Collections.reverse(mTempData);
                mExecutorService.execute(() -> mLocalDataSource.addEvents(mTempData));
                callback.onResult(mTempData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<Event> callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Events");
        Query newQuery = reference.orderByKey().endAt(params.key).limitToLast(params.requestedLoadSize);
        newQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Event> mTempData = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Event item = postSnapshot.getValue(Event.class);
                    if (item != null) {
                        if (!item.getUniqueId().equals(params.key))
                            mTempData.add(item);
                    }
                }
                Collections.reverse(mTempData);
                callback.onResult(mTempData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<Event> callback) {

    }

    @NonNull
    @Override
    public String getKey(@NonNull Event item) {
        return item.getUniqueId();
    }
}
