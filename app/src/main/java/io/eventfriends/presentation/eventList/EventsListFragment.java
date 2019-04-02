package io.eventfriends.presentation.eventList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.R;
import io.eventfriends.di.components.EventListComponent;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.presentation.splashActivity.SplashScreen;


public class EventsListFragment extends Fragment implements EventListView {

    //test
    private RecyclerView mRecyclerView;
    private EventListAdapter mListAdapter;
    private FloatingActionButton mFabButton;
    private NavController mNavController;

    @Inject
    public EventListPresenter mPresenter;

    public EventsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        EventListComponent component = EventFriendsApp.getInstance().getComponentsBuilder().getEventListComponent();
        component.injectEventList(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        mRecyclerView = view.findViewById(R.id.event_list_recycler);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(linearLayout);

        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2};

        mListAdapter = new EventListAdapter(data);
        mRecyclerView.setAdapter(mListAdapter);

        mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.main_host_fragment);

        mFabButton = view.findViewById(R.id.list_events_bottom_fab);
        mFabButton.setOnClickListener((v) -> {

            /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference eventsRef = database.getReference("Events");
            HashMap<String, String> event = new HashMap<>();
            event.put("data", "01.04.2019");
            event.put("userId", user.getUid());
            event.put("userName", "Chris Prat");
            event.put("eventType", "Cinema");
            eventsRef.push().setValue(event);

            HashMap<String, String> event2 = new HashMap<>();
            event2.put("data", "01.05.2012");
            event2.put("userId", user.getUid());
            event2.put("userName", "Tony Stark");
            event2.put("eventType", "Theater");
            eventsRef.push().setValue(event2);

            HashMap<String, String> event3 = new HashMap<>();
            event3.put("data", "01.05.2020");
            event3.put("userId", user.getUid());
            event3.put("userName", "Tony Stark");
            event3.put("eventType", "Concert");
            eventsRef.push().setValue(event3);

            List<Event> eventList = new ArrayList<>();
            Query newQuery = eventsRef.orderByChild("userName").equalTo("Tony Stark");
            newQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        Event event1 = postSnapshot.getValue(Event.class);
                        eventList.add(event1);
                    }
                    Log.d("EventList", eventList.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            Log.d("EventList", eventList.toString());


            DatabaseReference usersRef = database.getReference("Users");
            HashMap<String, String> newUser = new HashMap<>();
            newUser.put("id", user.getUid());
            newUser.put("imgUrl", (user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "default" ));
            newUser.put("name", user.getDisplayName());
            usersRef.setValue(newUser);*/


            mNavController.navigate(R.id.action_eventsListFragment_to_createEventFragment);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onAtach(this);
    }

    @Override
    public void updateList() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void startSplashScreen() {
        startActivity(SplashScreen.newIntent(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                mPresenter.signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        mPresenter.onDetach();
        super.onPause();
    }
}
