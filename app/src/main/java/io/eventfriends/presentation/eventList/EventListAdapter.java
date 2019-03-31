package io.eventfriends.presentation.eventList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.eventfriends.R;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventHolder> {

    private int[] mData;

    public EventListAdapter(int[] data) {
        mData = data;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        EventHolder holder = new EventHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.length;
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
    }
}
