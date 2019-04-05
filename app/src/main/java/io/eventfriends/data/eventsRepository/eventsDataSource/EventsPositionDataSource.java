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
import androidx.paging.PositionalDataSource;
import io.eventfriends.domain.entity.Event;

public class EventsPositionDataSource extends PositionalDataSource<Event> {

    private EventsLocalDataSource mLocalDataSource;
    private ExecutorService mExecutorService;
    private String lastKey;

    public EventsPositionDataSource(EventsLocalDataSource localDataSource, ExecutorService executorService) {
        mLocalDataSource = localDataSource;
        mExecutorService = executorService;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Event> callback) {
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
                lastKey = mTempData.get(mTempData.size()-1).getUniqueId();
                mExecutorService.execute(() -> mLocalDataSource.addEvents(mTempData));
                callback.onResult(mTempData, mTempData.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Event> callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Events");
        Query newQuery = reference.orderByKey().endAt(lastKey).limitToLast(params.loadSize);
        newQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Event> mTempData = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Event item = postSnapshot.getValue(Event.class);
                    if (item != null) {
                        if (!item.getUniqueId().equals(lastKey))
                            mTempData.add(item);
                    }
                }
                Collections.reverse(mTempData);
                lastKey = mTempData.get(mTempData.size()-1).getUniqueId();
                callback.onResult(mTempData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
