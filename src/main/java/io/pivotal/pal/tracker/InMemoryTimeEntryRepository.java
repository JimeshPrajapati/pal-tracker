package io.pivotal.pal.tracker;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements  TimeEntryRepository{

    List<TimeEntry> timeEntriesDB = new ArrayList<>();
    private Long currentId=1L;
    @Override
    public List<TimeEntry> list() {
        return timeEntriesDB;
    }

    @Override
    public TimeEntry find(Long timeEntryId) {
      return  timeEntriesDB.stream()
                           .filter(timeEntry1 -> timeEntry1.getId()==timeEntryId)
                           .findAny()
                           .orElse(null);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry newTimeEntry = new TimeEntry(currentId++,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
        timeEntriesDB.add(newTimeEntry);
        return newTimeEntry;
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        if (find(id) == null) return null;
        TimeEntry updatedEntry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        int index = timeEntriesDB.indexOf(find(id));
        timeEntriesDB.add(index,updatedEntry);
        return timeEntriesDB.get(index);
    }

    @Override
    public void delete(Long timeEntryId) {
        TimeEntry timeEntry = find(timeEntryId);
        timeEntriesDB.remove(timeEntry);
    }
}
