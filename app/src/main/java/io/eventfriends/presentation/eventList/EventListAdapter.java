package io.eventfriends.presentation.eventList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        EventHolder holder = new EventHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        holder.textView.setText(String.valueOf(mData[position]));
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewTest);
        }
    }
}
