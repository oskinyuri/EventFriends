package io.eventfriends.presentation.eventList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.R;
import io.eventfriends.data.eventsRepository.eventsDataSource.EventsRemoteDataFactory;
import io.eventfriends.di.components.EventListComponent;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.User;
import io.eventfriends.presentation.splashActivity.SplashScreen;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;


public class EventsListFragment extends Fragment implements EventListView {

    //test
    List<Event> mData;
    final String[] lastKey = new String[1];
    ExecutorService mExecutorService;
    EventsRemoteDataFactory mDataFactory;


    private CompositeDisposable disposable = new CompositeDisposable();
    Observable<PagedList<Event>> mRxData;
    Observable<PagedList<Event>> mSubject = PublishSubject.create();
    LiveData<PagedList<Event>> mLiveData;


    private ProgressBar mProgressBar;
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
        mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.main_host_fragment);

        setHasOptionsMenu(true);

        //Dagger
        EventListComponent component = EventFriendsApp.getInstance().getComponentsBuilder().getEventListComponent(getContext());
        component.injectEventList(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        mProgressBar = view.findViewById(R.id.progressBar);

        //init Recycler
        mRecyclerView = view.findViewById(R.id.event_list_recycler);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(linearLayout);
        mListAdapter = new EventListAdapter();
        //mRecyclerView.setAdapter(mListAdapter);

        mFabButton = view.findViewById(R.id.list_events_bottom_fab);
        mFabButton.setOnClickListener((v) -> {

            //mPresenter.loadEventsFormWeb();
            mNavController.navigate(R.id.action_eventsListFragment_to_createEventFragment);
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

    @SuppressWarnings("unchecked")
    @Override
    public void onStart() {
        super.onStart();

        //test
        /*mDataFactory = new EventsRemoteDataFactory();
        mExecutorService = Executors.newSingleThreadScheduledExecutor();
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(15)
                .setPageSize(10)
                .build();

        mLiveData = new LivePagedListBuilder(mDataFactory, pagedListConfig)
                .setFetchExecutor(mExecutorService)
                .build();
        mLiveData.observe(this, events -> mListAdapter.submitList(events));



        mRxData = new RxPagedListBuilder(mDataFactory, pagedListConfig)
                .setFetchScheduler(Schedulers.io())
                .buildObservable();
        disposable.add(mRxData.subscribe(mListAdapter::submitList));

        disposable.add(getRxData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mListAdapter::submitList));*/


    }

    @SuppressWarnings("unchecked")
    /*private Observable<PagedList<Event>> getRxData() {

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(15)
                .setPageSize(10)
                .build();

        return new RxPagedListBuilder(mDataFactory, pagedListConfig)
                .buildObservable().startWith(new RxPagedListBuilder(mDataFactory, pagedListConfig)
                        .buildObservable());
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onAttach(this);
       /* ConnectionObservable connectionObservable = new ConnectionObservable(getContext());

        Disposable disposable = connectionObservable.subscribe(connection -> {
            if (connection.getIsConnected()) {
                switch (connection.getType()) {
                    case ConnectionModel.WIFI_DATA:
                        hideProgress();
                        break;
                    case ConnectionModel.MOBILE_DATA:
                        hideProgress();
                        break;
                }
            } else {
                showProgress();
            }
        });*/

        /*connectionObservable.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connection) {
                if (connection.getIsConnected()) {
                    switch (connection.getType()) {
                        case ConnectionModel.WIFI_DATA:
                            hideProgress();
                            break;
                        case ConnectionModel.MOBILE_DATA:
                            hideProgress();
                            break;
                    }
                } else {
                    showProgress();
                }
            }
        });*/

        //TODO Убрать прям ща комент
        LiveData<PagedList<Event>> liveData = mPresenter.loadEvents();
        liveData.observe(this, pagedList -> {
            mListAdapter.submitList(pagedList);
        });
        mRecyclerView.setAdapter(mListAdapter);
        mPresenter.updateEventsList();


    }

    @Override
    public void updateList(PagedList<Event> eventList) {
        mListAdapter.submitList(eventList);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
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

        //Test delete
        disposable.clear();

        mPresenter.onDetach();
        super.onPause();
    }
}
