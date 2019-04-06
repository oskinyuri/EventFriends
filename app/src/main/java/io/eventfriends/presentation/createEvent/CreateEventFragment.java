package io.eventfriends.presentation.createEvent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.R;
import io.eventfriends.di.components.CreateEventComponent;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.presentation.pageEvent.PageEventFragment;

public class CreateEventFragment extends Fragment implements CreateEventView {


    NavController navController;

    @Inject
    public CreateEventPresenter mPresenter;

    private ConstraintLayout mCreateEventLayout;

    private EditText mTitleET;
    private EditText mDateET;
    private EditText mTimeED;
    private EditText mLocationET;
    private EditText mEventLinkET;
    private EditText mFeedbackET;
    private EditText mAdditionalInfoED;
    private List<EditText> mEditTexts;

    private BetterSpinner mEventTypeSpinner;

    private MaterialButton mCreateBtn;

    private Event mEvent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.main_host_fragment);
        CreateEventComponent component = EventFriendsApp.getInstance().getComponentsBuilder().getCreateEventComponent();
        component.injectCreateEvent(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mCreateEventLayout = view.findViewById(R.id.create_event_layout);
        mEditTexts = new ArrayList<>();
        //init edit texts

        mTitleET = view.findViewById(R.id.create_event_title_edit_text);
        mEditTexts.add(mTitleET);

        mDateET = view.findViewById(R.id.create_event_date_edit_text);
        mEditTexts.add(mDateET);

        mTimeED = view.findViewById(R.id.create_event_time_edit_text);
        mEditTexts.add(mTimeED);

        mLocationET = view.findViewById(R.id.create_event_location_edit_text);
        mEditTexts.add(mLocationET);

        mEventLinkET = view.findViewById(R.id.create_event_event_link_edit_text);
        mEditTexts.add(mEventLinkET);

        mFeedbackET = view.findViewById(R.id.create_event_person_link_edit_text);
        mEditTexts.add(mFeedbackET);

        mAdditionalInfoED = view.findViewById(R.id.create_event_additional_info_edit_text);
        mEditTexts.add(mAdditionalInfoED);

        //init btn
        mCreateBtn = view.findViewById(R.id.create_event_create_btn);

        //init spinner
        mEventTypeSpinner = view.findViewById(R.id.create_event_type_spinner);
        mEditTexts.add(mEventTypeSpinner);

        String[] eventTypes = getResources().getStringArray(R.array.event_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_dropdown_item_1line, eventTypes);
        mEventTypeSpinner = view.findViewById(R.id.create_event_type_spinner);
        mEventTypeSpinner.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        initListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onAttach(this);
    }

    private void initListeners() {

        mEventTypeSpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEventTypeSpinner.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCreateBtn.setOnClickListener(v -> {

            if (!hasEmptyFields()) {
                mEvent = new Event();
                mEvent.setTitleEvent(mTitleET.getText().toString());
                mEvent.setDateEvent(mDateET.getText().toString());
                mEvent.setTimeEvent(mTimeED.getText().toString());
                mEvent.setLocationEvent(mLocationET.getText().toString());
                mEvent.setEventLink(mEventLinkET.getText().toString());
                mEvent.setEventType(mEventTypeSpinner.getText().toString());
                mEvent.setUserFeedbackLink(mFeedbackET.getText().toString());
                mEvent.setAdditionalInfo(mAdditionalInfoED.getText().toString());

                //TODO uncomment it, it work code
                //mPresenter.setEvent(mEvent);
                startPageEventFragment(mEvent);
            }

        });
    }

    public void startPageEventFragment(Event event) {
        //TODO после получения state LOADED передавать ключ Event's key. Не удалять переделать
        /*CreateEventFragmentDirections.OpenEventAfterCreateAction action = CreateEventFragmentDirections.openEventAfterCreateAction(event, 2);
        navController.navigate(action);*/
        navController.navigate(R.id.openEventAfterCreateAction);
    }

    @Override
    public void onPause() {
        mPresenter.onDetach();
        super.onPause();
    }

    private boolean hasEmptyFields() {
        boolean hasError = false;

        for (EditText editText : mEditTexts) {
            if (editText.getText().toString().trim().equalsIgnoreCase("")) {
                editText.setError("This field can not be blank");
                hasError = true;
            }
        }
        return hasError;
    }
}
