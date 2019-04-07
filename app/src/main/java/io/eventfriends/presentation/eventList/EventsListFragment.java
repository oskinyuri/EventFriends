package io.eventfriends.presentation.eventList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.R;
import io.eventfriends.di.components.EventListComponent;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.User;
import io.eventfriends.presentation.splashActivity.SplashScreen;


public class EventsListFragment extends Fragment implements EventListView {

    private RecyclerView mRecyclerView;
    private EventListAdapter mListAdapter;
    private FloatingActionButton mFabButton;
    private NavController mNavController;
    private SwipeRefreshLayout mRefreshLayout;

    @Inject
    public EventListPresenter mPresenter;

    public EventsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.main_host_fragment);

        setHasOptionsMenu(true);

        //Dagger
        EventListComponent component = EventFriendsApp.getInstance().getComponentsBuilder().getEventListComponent(getContext());
        component.injectEventList(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        //mProgressBar = view.findViewById(R.id.progressBar);

        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.updateEventsList());

        //init Recycler
        mRecyclerView = view.findViewById(R.id.event_list_recycler);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(linearLayout);
        mListAdapter = new EventListAdapter();
        mListAdapter.setOnItemClickListener(v -> {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            String eventKey = Objects.requireNonNull(Objects.requireNonNull(mListAdapter.getCurrentList()).get(position)).getUniqueId();
            EventsListFragmentDirections.OpenEventFromList action = EventsListFragmentDirections.openEventFromList(eventKey);
            mNavController.navigate(action);
        });
        mRecyclerView.setAdapter(mListAdapter);

        mFabButton = view.findViewById(R.id.list_events_bottom_fab);
        mFabButton.setOnClickListener((v) -> {
            mNavController.navigate(R.id.createEventAction);
        });
        return view;
    }

    //Yet need for tests
    private void loadInDBItemsTestMethod() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsRef = database.getReference("Events");
        for (int i = 0; i < 50; i++) {
            Event mEvent = new Event();
            mEvent.setTitleEvent("item " + String.valueOf(i));
            mEvent.setDateEvent("item " + String.valueOf(i));
            mEvent.setTimeEvent("item " + String.valueOf(i));
            mEvent.setLocationEvent("item " + String.valueOf(i));
            mEvent.setEventLink("item " + String.valueOf(i));
            mEvent.setEventType("item " + String.valueOf(i));
            mEvent.setUserFeedbackLink("item " + String.valueOf(i));
            mEvent.setAdditionalInfo("item " + String.valueOf(i));

            String uniqueId = eventsRef.push().getKey();
            // Set firebase generated if
            mEvent.setUniqueId(uniqueId);

            // Set info about user
            mEvent.setUserId("item " + String.valueOf(i));
            mEvent.setUserName("item " + String.valueOf(i));
            mEvent.setUserPhotoUrl(User.DEFAULT_PROFLIE_IMG);

            // Set timestamp
            mEvent.setTimestamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));

            if (uniqueId != null)
                eventsRef.child(uniqueId).setValue(mEvent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onAttach(this);
    }

    @Override
    public void updateList(PagedList<Event> eventList) {
        mListAdapter.submitList(eventList);
    }

    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
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
