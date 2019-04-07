package io.eventfriends.presentation.pageEvent;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import io.eventfriends.EventFriendsApp;
import io.eventfriends.R;
import io.eventfriends.di.components.EventPageComponent;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.User;

public class EventPageFragment extends Fragment implements EventPageView {

    private Event mEvent;
    private String mKey;

    private CardView mLocationCardView;
    private CardView mLinkEventCardView;
    private CardView mLinkPersonCardView;

    private ImageView mProfileImg;

    private TextView mCreatorNameTV;
    private TextView mDateTV;
    private TextView mTimeTV;
    private TextView mEventTypeTV;
    private TextView mAddressTV;
    private TextView mLinkOnEventTV;
    private TextView mLinkOnCreator;
    private TextView mAdditionalInfoTV;

    @Inject
    public EventPagePresenter mPresenter;

    private ProgressBar mProgressBar;

    NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.main_host_fragment);

        EventPageComponent component = EventFriendsApp.getInstance().getComponentsBuilder().getEventPageComponent();
        component.injectEventPage(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_event_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        mKey = EventPageFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getEventKey();
        initViews(view);
    }

    private void initViews(View view) {

        mProgressBar = view.findViewById(R.id.pageProgressBar);

        mProfileImg = view.findViewById(R.id.page_event_profile_photo);

        mCreatorNameTV = view.findViewById(R.id.page_event_creator_name_text_view);
        mDateTV = view.findViewById(R.id.page_event_date_text_view);
        mTimeTV = view.findViewById(R.id.page_event_time_text_view);
        mEventTypeTV = view.findViewById(R.id.page_event_type_text_view);
        mAddressTV = view.findViewById(R.id.page_event_address_text_view);
        mLinkOnEventTV = view.findViewById(R.id.page_event_link_text_view);
        mLinkOnCreator = view.findViewById(R.id.page_event_creator_link_text_view);
        mAdditionalInfoTV = view.findViewById(R.id.page_event_additional_info_text_view);

        mLocationCardView = view.findViewById(R.id.location_card_view);
        mLocationCardView.setOnClickListener((v) -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + mEvent.getLocationEvent());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        mLinkEventCardView = view.findViewById(R.id.event_link_card_view);
        mLinkEventCardView.setOnClickListener((v) -> {
            String url = mEvent.getEventLink();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(url));
            try {
                startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();

                Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, url);
                startActivity(searchIntent);
            }
        });

        mLinkPersonCardView = view.findViewById(R.id.person_link_card_view);
        mLinkPersonCardView.setOnClickListener(v -> {
            String url = mEvent.getUserFeedbackLink();
            Intent browserPersonIntent = new Intent(Intent.ACTION_VIEW);
            browserPersonIntent.setData(Uri.parse(url));
            try {
                startActivity(browserPersonIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();

                Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, url);
                startActivity(searchIntent);
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onAttach(this);
        mPresenter.getEvent(mKey);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEvent(Event event) {
        mEvent = event;
        setDataInViews();
    }

    private void setDataInViews() {

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity()))
                .getSupportActionBar())
                .setTitle(mEvent.getTitleEvent());

        mCreatorNameTV.setText(mEvent.getUserName());
        mDateTV.setText(mEvent.getDateEvent());
        mTimeTV.setText(mEvent.getTimeEvent());
        mEventTypeTV.setText(mEvent.getEventType());
        mAddressTV.setText(mEvent.getLocationEvent());
        mLinkOnEventTV.setText(mEvent.getEventLink());
        mLinkOnCreator.setText(mEvent.getUserFeedbackLink());
        mAdditionalInfoTV.setText(mEvent.getAdditionalInfo());

        if (!mEvent.getUserPhotoUrl().equals(User.DEFAULT_PROFLIE_IMG)) {
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(mEvent.getUserPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mProfileImg);
        } else {
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(R.drawable.default_user_image)
                    .into(mProfileImg);
        }
    }

    @Override
    public void onPause() {
        mPresenter.onDetach();
        super.onPause();
    }
}
