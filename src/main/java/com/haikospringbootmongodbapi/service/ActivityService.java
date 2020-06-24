package com.haikospringbootmongodbapi.service;

import com.haikospringbootmongodbapi.repository.ActivityRepository;
import com.haikospringbootmongodbapi.repository.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final HistoryService historyService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, HistoryService historyService) {
        this.activityRepository = activityRepository;
        this.historyService = historyService;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public ResponseEntity<Activity> getActivityById(Long id)
            throws ResourceNotFoundException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity with this id not found"));
        return ResponseEntity.ok().body(activity);
    }

    public Activity createActivity(String title, String summary, String description, String info) {

        Activity activity = new Activity();
        activity.setId(activityRepository.count());
        activity.setTitle(title);
        activity.setSummary(summary);
        activity.setDescription(description);
        activity.setStartDateTime(LocalDateTime.now());
        activity.setEndDateTime(LocalDateTime.now());
        activity.setInfo(info);

        historyService.createActivity(title, summary, description, info);

        return activityRepository.save(activity);
    }

    public Activity updateActivity(Long id, String title, String summary, String description, String info) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity with this id not found"));

        if (title != null && !title.equals(activity.getTitle())) {
            historyService.updateTitle(activity.getTitle(), title);
            activity.setTitle(title);
        }

        if (summary != null && !summary.equals(activity.getSummary())) {
            historyService.updateSummary(activity.getSummary(), summary);

            activity.setSummary(summary);
        }

        if (description != null && !description.equals(activity.getDescription())) {
            historyService.updateDescription(activity.getDescription(), description);
            activity.setDescription(description);
        }

        historyService.updateEndDateTime(activity.getEndDateTime());
        activity.setEndDateTime(LocalDateTime.now());

        if (info != null && !info.equals(activity.getInfo())) {
            historyService.updateInfo(activity.getInfo(), info);
            activity.setInfo(info);
        }
        return activityRepository.save(activity);
    }


    public Map<String, Boolean> deleteActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity with this id not found"));

        activityRepository.delete(activity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", TRUE);
        return response;
    }
}
