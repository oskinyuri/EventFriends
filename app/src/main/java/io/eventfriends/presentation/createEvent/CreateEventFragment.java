package io.eventfriends.presentation.createEvent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.weiwangcn.betterspinner.library.BetterSpinner;

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

public class CreateEventFragment extends Fragment implements CreateEventView {



    NavController navController;

    @Inject
    public CreateEventPresenter mPresenter;

    private ConstraintLayout mCreateEventLayout;

    private EditText mDateET;
    private EditText mTimeED;
    private EditText mLocationET;
    private EditText mEventLinkET;
    private EditText mFeedbackET;
    private EditText mAdditionalInfoED;

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
        //init edit texts
        mDateET = view.findViewById(R.id.create_event_date_edit_text);
        mTimeED = view.findViewById(R.id.create_event_time_edit_text);
        mLocationET = view.findViewById(R.id.create_event_location_edit_text);
        mEventLinkET = view.findViewById(R.id.create_event_event_link_edit_text);
        mFeedbackET = view.findViewById(R.id.create_event_person_link_edit_text);
        mAdditionalInfoED = view.findViewById(R.id.create_event_additional_info_edit_text);

        //init btn
        mCreateBtn = view.findViewById(R.id.create_event_create_btn);

        //init spinner
        mEventTypeSpinner = view.findViewById(R.id.create_event_type_spinner);
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

            if(!hasEmptyFields(mCreateEventLayout)){
                mEvent = new Event();
                mEvent.setDateEvent(mDateET.getText().toString());
                mEvent.setTimeEvent(mTimeED.getText().toString());
                mEvent.setLocationEvent(mLocationET.getText().toString());
                mEvent.setEventLink(mEventLinkET.getText().toString());
                mEvent.setEventType(mEventTypeSpinner.getText().toString());
                mEvent.setUserFeedbackLink(mFeedbackET.getText().toString());
                mEvent.setAdditionalInfo(mAdditionalInfoED.getText().toString());

                mPresenter.setEvent(mEvent);
            }

        });
    }

    @Override
    public void onPause() {
        mPresenter.onDetach();
        super.onPause();
    }

    /**
     * Проходим по всем edit text и проверяем на пустоту
     *
     * @param viewGroup - корневая viewGroup
     */
    private boolean hasEmptyFields(ViewGroup viewGroup) {
        boolean hasError = false;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                hasEmptyFields((ViewGroup) child);
            }  else if (child instanceof EditText) {
                if (((EditText) child).getText().toString().trim().equalsIgnoreCase("")) {
                    ((EditText) child).setError("This field can not be blank");
                    hasError = true;
                }
            }
        }
        return hasError;
    }
}
