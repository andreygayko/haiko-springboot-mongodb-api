package com.haikospringbootmongodbapi.service;

import com.haikospringbootmongodbapi.repository.HistoryRepository;
import com.haikospringbootmongodbapi.repository.model.Change;
import com.haikospringbootmongodbapi.repository.model.History;
import com.haikospringbootmongodbapi.repository.model.Type;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    public Map<String, Boolean> deleteAllHistory() {
        historyRepository.deleteAll();
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", TRUE);
        return response;
    }

    public void createActivity(String title, String summary, String description, String info) {
        create(new Change("title", null, title));
        create(new Change("summary", null, summary));
        create(new Change("description", null, description));
        create(new Change("startDateTime", null, LocalDateTime.now()));
        create(new Change("endDateTime", null, LocalDateTime.now()));
        create(new Change("info", null, info));
    }

    public void updateTitle(String oldTitle, String newTitle) {
        update(new Change("title", oldTitle, newTitle));
    }

    public void updateSummary(String oldSummary, String newSummary) {
        update(new Change("summary", oldSummary, newSummary));
    }

    public void updateDescription(String oldDescription, String newDescription) {
        update(new Change("description", oldDescription, newDescription));
    }

    public void updateEndDateTime(LocalDateTime oldDateTime) {
        update(new Change("endDateTime", oldDateTime, LocalDateTime.now()));
    }

    public void updateInfo(String oldInfo, String newInfo) {
        update(new Change("info", oldInfo, newInfo));
    }

    public History update(Change change) {
        History history = new History();
        history.setChange(change);
        history.setType(Type.UPDATE);
        history.setDateTime(LocalDateTime.now());

        return historyRepository.save(history);
    }

    public void create(Change change) {
        History history = new History();
        history.setChange(change);
        history.setType(Type.COMPOSE);
        history.setDateTime(LocalDateTime.now());
        historyRepository.save(history);
    }

}
