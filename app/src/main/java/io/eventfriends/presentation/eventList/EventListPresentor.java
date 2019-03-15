package io.eventfriends.presentation.eventList;

public class EventListPresentor {

    private EventListView mView;

    public void onAtach(EventListView view){
        mView = view;
    }

    public void onDetach(){
        mView = null;
    }

}
