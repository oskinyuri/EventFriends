package io.eventfriends.presentation.pageEvent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import io.eventfriends.R;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.User;

public class PageEventFragment extends Fragment {

    private Event mEvent;

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

    NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.main_host_fragment);
        //TODO в тулбар после полученния данных выводить название event'а
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_event_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initViews(view);

        //TODO получать ключ Event'а и сетить данные
        //setDataInViews();
    }

    private void initViews(View view) {

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
            startActivity(browserIntent);
        });

        mLinkPersonCardView = view.findViewById(R.id.person_link_card_view);
        mLinkPersonCardView.setOnClickListener(v -> {
            String url = mEvent.getUserFeedbackLink();
            Intent browserPersonIntent = new Intent(Intent.ACTION_VIEW);
            browserPersonIntent.setData(Uri.parse(url));
            startActivity(browserPersonIntent);

        });
    }

    private void setDataInViews() {
        mCreatorNameTV.setText(mEvent.getUserName());
        mDateTV.setText(mEvent.getDateEvent());
        mTimeTV.setText(mEvent.getTimeEvent());
        mEventTypeTV.setText(mEvent.getEventType());
        mAddressTV.setText(mEvent.getLocationEvent());
        mLinkOnEventTV.setText(mEvent.getEventLink());
        mLinkOnCreator.setText(mEvent.getUserFeedbackLink());
        mAdditionalInfoTV.setText(mEvent.getAdditionalInfo());

        if (!mEvent.getUserPhotoUrl().equals(User.DEFAULT_PROFLIE_IMG)){
            Glide.with(getContext())
                    .load(mEvent.getUserPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mProfileImg);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.default_user_image)
                    .into(mProfileImg);
        }
    }
}
