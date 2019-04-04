package io.eventfriends.data.eventsRepository.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import io.eventfriends.domain.entity.Event;

@Database(entities = Event.class, version = 1, exportSchema = false)
public abstract class EventsDB extends RoomDatabase {
    public abstract EventsDAO getEventsDAO();
}
