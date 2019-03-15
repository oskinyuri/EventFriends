package io.eventfriends.presentation.createEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import io.eventfriends.R;

public class CreateEventFragment extends Fragment {

    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.main_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.eventsListFragment).build();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        mToolbar = view.findViewById(R.id.fragment_create_event_toolbar);
        NavigationUI.setupWithNavController(mToolbar, mNavController, mAppBarConfiguration);
        return view;
    }
}
