package com.yandex.kanban.service;

import com.yandex.kanban.model.Task;

import java.util.List;

public class InMemoryHistoryManager extends InMemoryTaskManager implements HistoryManager{
    @Override
    public void add(Task task) { // должен помечать задачи как просмотренные

    }

    @Override
    public List<Task> getHistory() {
        System.out.println(viewedTask);
        return viewedTask;
    }
}

