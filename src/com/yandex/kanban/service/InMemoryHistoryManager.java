package com.yandex.kanban.service;

import com.yandex.kanban.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> browsingHistory = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (browsingHistory.size() != 10) {
            browsingHistory.add(task);
        } else {
            browsingHistory.remove(0);
            browsingHistory.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return browsingHistory;
    }
}

