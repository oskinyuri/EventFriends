package io.eventfriends.data.eventsRepository.eventsListDataSource;

import android.content.Context;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Room;
import io.eventfriends.data.eventsRepository.eventsListDataSource.db.EventsDB;
import io.eventfriends.domain.entity.Event;

public class EventsLocalDataSource {

    private static String DATABASE_NAME = "EVENTS_DATABASE";

    private EventsDB mEventsDB;

    public EventsLocalDataSource(Context context) {
        mEventsDB = Room.databaseBuilder(context, EventsDB.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public void addEvents(List<Event> eventList){
        mEventsDB.getEventsDAO().addEvents(eventList);
    }

    public DataSource.Factory<Integer, Event> getEvents(){
        return mEventsDB.getEventsDAO().getPagedList();
    }

    public void deleteAll(){
        mEventsDB.getEventsDAO().deleteAllEvents();
    }

}
