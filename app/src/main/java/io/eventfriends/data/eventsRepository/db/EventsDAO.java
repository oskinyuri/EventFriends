package io.eventfriends.data.eventsRepository.db;

import java.util.List;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.eventfriends.domain.entity.Event;

@Dao
public interface EventsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEvents(List<Event> eventList);

    @Query("SELECT * FROM event")
    DataSource.Factory<Integer, Event> getPagedList();

    @Query("delete from Event")
    void deleteAllEvents();
}
