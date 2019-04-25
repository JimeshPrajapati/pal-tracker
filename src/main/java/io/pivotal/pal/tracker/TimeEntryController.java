package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class TimeEntryController {

    //@Autowired
    TimeEntryRepository timeRepo;

    @GetMapping(value="/get")
    public ResponseEntity<List<TimeEntry>> list()
    {
        return new ResponseEntity<>(timeRepo.list(),HttpStatus.OK);
    }

    @GetMapping (value="/get/{Id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long Id)
    {
        TimeEntry timeEntry = timeRepo.find(Id);
        if(timeEntry==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(timeEntry,HttpStatus.OK);
    }

    public TimeEntryController(TimeEntryRepository timeRepo)
    {
        this.timeRepo=timeRepo;
    }

    @PostMapping (value="/create")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry)
    {
        TimeEntry newTimeEntry= timeRepo.create(timeEntry);
        return new ResponseEntity<>(newTimeEntry, HttpStatus.CREATED);
    }

    @PutMapping(value="/put/{Id}")
    public ResponseEntity update(@PathVariable Long Id,@RequestBody TimeEntry timeEntry)
    {
        TimeEntry updatedTimeEntry = timeRepo.update(Id,timeEntry);
        if(updatedTimeEntry==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
    }

    @DeleteMapping(value="/delete/{Id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long Id)
    {
        timeRepo.delete(Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
