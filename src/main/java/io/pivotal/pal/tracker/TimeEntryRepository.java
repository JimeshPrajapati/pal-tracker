package io.pivotal.pal.tracker;

import java.sql.Time;
import java.util.List;

public interface TimeEntryRepository {

    List<TimeEntry> list();
    TimeEntry find(Long timeEntryId);
    TimeEntry create(TimeEntry timeEntry);
    TimeEntry update(Long id,TimeEntry timeEntry);
    void delete(Long timeEntryId);
}
