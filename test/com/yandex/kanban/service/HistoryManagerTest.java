package com.yandex.kanban.service;

import com.yandex.kanban.model.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    InMemoryTaskManager taskManager = new InMemoryTaskManager();


    @Test
    void checkEmptyHistory() {
        SimpleTask simpleTask = new SimpleTask();
        taskManager.createSimpleTask(simpleTask);
        historyManager.add(simpleTask);

        assertEquals(1, historyManager.getHistory().size(), "Задача не добавилась в историю");
    }

    @Test
    void checkDoubleInHistory() {
        SimpleTask simpleTask = new SimpleTask();
        taskManager.createSimpleTask(simpleTask);
        taskManager.getSimpleTaskById(1);
        taskManager.getSimpleTaskById(1);

        assertEquals(1, taskManager.getHistory().size());
    }
}