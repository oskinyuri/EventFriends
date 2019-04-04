package io.eventfriends.presentation.eventList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.eventfriends.R;
import io.eventfriends.domain.entity.Event;
import io.eventfriends.domain.entity.User;

public class EventListAdapter extends PagedListAdapter<Event, EventListAdapter.EventHolder> {

    private int[] mData;

    public EventListAdapter() {
        super(Event.DIFF_CALLBACK);
    }

    public void submitList(@Nullable PagedList<Event> pagedList) {
        super.submitList(pagedList);
    }

    /*public EventListAdapter(int[] data) {
        mData = data;
    }*/

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        EventHolder holder = new EventHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        holder.bindOn(getItem(position));
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        public TextView eventType;
        public TextView eventTitle;
        public TextView eventText;

        public MaterialButton msgBtn;
        public MaterialButton moreBtn;
        public MaterialButton hideBtn;

        public ImageButton likeBtn;

        public ImageView profilePhoto;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventType = itemView.findViewById(R.id.event_item_type);
            eventTitle = itemView.findViewById(R.id.event_item_title);
            eventText = itemView.findViewById(R.id.event_item_text);

            msgBtn = itemView.findViewById(R.id.event_item_msg_btn);
            moreBtn = itemView.findViewById(R.id.event_item_more_btn);
            hideBtn = itemView.findViewById(R.id.event_item_hide_btn);
            likeBtn = itemView.findViewById(R.id.event_item_like_btn);

            profilePhoto = itemView.findViewById(R.id.event_item_profile_photo);
        }

        public void bindOn(Event event){
            eventType.setText(event.getEventType());
            eventTitle.setText(event.getTitleEvent());
            eventText.setText(event.getAdditionalInfo());

            if (!event.getUserPhotoUrl().equals(User.DEFAULT_PROFLIE_IMG)){
                Glide.with(itemView.getContext())
                        .load(event.getUserPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(profilePhoto);
            } else {
                Glide.with(itemView.getContext())
                        .load(R.drawable.default_user_image)
                        .into(profilePhoto);
            }
        }
    }
}
