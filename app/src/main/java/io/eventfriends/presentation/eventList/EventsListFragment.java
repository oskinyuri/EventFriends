package io.eventfriends.presentation.eventList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.eventfriends.R;
import io.eventfriends.presentation.splashActivity.SplashScreen;


public class EventsListFragment extends Fragment implements EventListView {

    //test
    private RecyclerView mRecyclerView;
    private EventListAdapter mListAdapter;
    private FloatingActionButton mFabButton;
    private NavController mNavController;

    public EventListPresenter mPresenter;

    public EventsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        mRecyclerView = view.findViewById(R.id.event_list_recycler);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(linearLayout);

        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2,
                3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7,
                8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        mListAdapter = new EventListAdapter(data);
        mRecyclerView.setAdapter(mListAdapter);

        mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.main_host_fragment);

        mFabButton = view.findViewById(R.id.list_events_bottom_fab);
        mFabButton.setOnClickListener((v) -> mNavController.navigate(R.id.action_eventsListFragment_to_createEventFragment));
        return view;
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
        super.onCreateOptionsMenu(menu, inflater);
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
}
