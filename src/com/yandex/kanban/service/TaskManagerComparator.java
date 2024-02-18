package com.yandex.kanban.service;

import com.yandex.kanban.model.SimpleTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class TaskManagerComparator {
    private final TreeSet<SimpleTask> tasks;

    public TaskManagerComparator() {
        tasks = new TreeSet<>(Comparator.comparing(SimpleTask::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));
    }

    public void addTask(SimpleTask task) {
        tasks.add(task);
    }

    public List<SimpleTask> getPrioritizedTasks() {
        return new ArrayList<>(tasks);
    }
}
