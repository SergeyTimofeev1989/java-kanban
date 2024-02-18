package com.yandex.kanban.service;

import com.yandex.kanban.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void start() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void changeStatusWithEmptyList() {
        EpicTask epicTask = new EpicTask();
        taskManager.createEpicTask(epicTask);
        taskManager.changeStatus(epicTask);

        assertEquals(Status.NEW, epicTask.getStatus());
    }

    @Test
    void changeStatusWhenAllSubtasksNEW() {
        EpicTask epicTask = new EpicTask();

        SubTask subTaskOne = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now(), epicTask);
        SubTask subTaskTwo = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now().plusHours(1), epicTask);
        taskManager.createSubTask(subTaskOne);
        taskManager.createSubTask(subTaskTwo);
        taskManager.changeStatus(epicTask);

        assertEquals(Status.NEW, epicTask.getStatus());
    }

    @Test
    void changeStatusWhenAllSubtasksDONE() {
        EpicTask epicTask = new EpicTask();

        SubTask subTaskOne = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(10), LocalDateTime.now(), epicTask);
        SubTask subTaskTwo = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(10), LocalDateTime.now().plusHours(1), epicTask);
        taskManager.createSubTask(subTaskOne);
        taskManager.createSubTask(subTaskTwo);
        taskManager.changeStatus(epicTask);

        assertEquals(Status.DONE, epicTask.getStatus());
    }

    @Test
    void changeStatusWithNEWandDONE() {
        EpicTask epicTask = new EpicTask();

        SubTask subTaskOne = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now(), epicTask);
        SubTask subTaskTwo = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(10), LocalDateTime.now().plusHours(1), epicTask);
        taskManager.createSubTask(subTaskOne);
        taskManager.createSubTask(subTaskTwo);
        taskManager.changeStatus(epicTask);

        assertEquals(Status.IN_PROGRESS, epicTask.getStatus());
    }

    @Test
    void changeStatusWhenAllSubtasksINPROGRESS() {
        EpicTask epicTask = new EpicTask();

        SubTask subTaskOne = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(10), LocalDateTime.now(), epicTask);
        SubTask subTaskTwo = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(10), LocalDateTime.now().plusHours(1), epicTask);
        taskManager.createSubTask(subTaskOne);
        taskManager.createSubTask(subTaskTwo);
        taskManager.changeStatus(epicTask);

        assertEquals(Status.IN_PROGRESS, epicTask.getStatus());
    }
}