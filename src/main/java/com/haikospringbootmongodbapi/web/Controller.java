package com.haikospringbootmongodbapi.web;

import com.haikospringbootmongodbapi.repository.model.Activity;
import com.haikospringbootmongodbapi.repository.model.History;
import com.haikospringbootmongodbapi.service.ActivityService;
import com.haikospringbootmongodbapi.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class Controller {

    private final ActivityService activityService;
    private final HistoryService historyService;

    @Autowired
    public Controller(ActivityService activityService, HistoryService historyService) {
        this.activityService = activityService;
        this.historyService = historyService;
    }

    @GetMapping("/activities")
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/activities/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable("id") Long id)
            throws ResourceNotFoundException {
        return activityService.getActivityById(id);
    }

    @PostMapping("/activities")
    public Activity createActivity(
            @RequestParam(value = "title", required = true) @NotNull @Size(max = 100) String title,
            @RequestParam(value = "summary", required = true) @NotNull @Size(max = 25) String summary,
            @RequestParam(value = "description", required = true) @NotNull @Size(max = 200) String description,
            @RequestParam(value = "info", required = false) String info) {

        return activityService.createActivity(title, summary, description, info);
    }

    @PutMapping("/activities/{id}")
    public Activity updateActivity(@PathVariable("id") Long id,
                                   @RequestParam(value = "title", required = false) @Size(max = 100) String title,
                                   @RequestParam(value = "summary", required = false) @Size(max = 25) String summary,
                                   @RequestParam(value = "description", required = false) @Size(max = 200) String description,
                                   @RequestParam(value = "info", required = false) String info) {

        return activityService.updateActivity(id, title, summary, description, info);
    }

    //Not in task, but helps with testing
    @DeleteMapping("/activities/{id}")
    public Map<String, Boolean> deleteActivity(@PathVariable("id") Long id) {
        return activityService.deleteActivity(id);
    }

    @GetMapping("/history")
    public List<History> getAllHistory() {
        return historyService.getAllHistory();
    }

    @DeleteMapping("/history")
    public Map<String, Boolean> deleteAllHistory() {
        return historyService.deleteAllHistory();
    }
}
