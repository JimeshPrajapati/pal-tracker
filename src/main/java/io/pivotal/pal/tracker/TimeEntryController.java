package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    //@Autowired
    TimeEntryRepository timeRepo;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeRepo, MeterRegistry meterRegistry)
    {
        this.timeRepo=timeRepo;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list()
    {
        actionCounter.increment();
        return new ResponseEntity<>(timeRepo.list(),HttpStatus.OK);
    }

    @GetMapping("{Id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long Id)
    {
        TimeEntry timeEntry = timeRepo.find(Id);
        if(timeEntry==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        actionCounter.increment();
        return new ResponseEntity<>(timeEntry,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry)
    {
        TimeEntry newTimeEntry= timeRepo.create(timeEntry);
        actionCounter.increment();
        timeEntrySummary.record(timeRepo.list().size());
        return new ResponseEntity<>(newTimeEntry, HttpStatus.CREATED);
    }

    @PutMapping("{Id}")
    public ResponseEntity update(@PathVariable Long Id,@RequestBody TimeEntry timeEntry)
    {
        TimeEntry updatedTimeEntry = timeRepo.update(Id,timeEntry);
        if(updatedTimeEntry==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        actionCounter.increment();
        return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
    }

    @DeleteMapping("{Id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long Id)
    {
        timeRepo.delete(Id);
        actionCounter.increment();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
