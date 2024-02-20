package com.yandex.kanban.model;

import com.yandex.kanban.service.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTaskTest {

    public InMemoryTaskManager taskManager = new InMemoryTaskManager();
    public EpicTask epicTask = new EpicTask("name", "description", Status.NEW);


    @BeforeEach
    void setUp() {
        taskManager.createEpicTask(epicTask);
        taskManager.clearAllSubTasks();
    }

    @Test
    void emptyListOfSubTasksShouldSetNewStatus() {

        assertEquals(Status.NEW, epicTask.getStatus());
    }

    @Test
    void allSubTasksWithStatusNewShouldSetNewStatus() throws Exception {
        SubTask subTaskOne = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(1), LocalDateTime.now(), epicTask);
        SubTask subTaskTwo = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(1), LocalDateTime.now().plusMinutes(5), epicTask);
        SubTask subTaskThree = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(1), LocalDateTime.now().plusMinutes(10), epicTask);

        taskManager.createSubTask(subTaskOne);
        taskManager.createSubTask(subTaskTwo);
        taskManager.createSubTask(subTaskThree);

        assertEquals(Status.NEW, epicTask.getStatus());
    }

    @Test
    void allSubTasksWithStatusDoneShouldSetDoneStatus() throws Exception {
        SubTask subTaskOne = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(1), LocalDateTime.now(), epicTask);
        SubTask subTaskTwo = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(1), LocalDateTime.now().plusMinutes(5), epicTask);
        SubTask subTaskThree = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(1), LocalDateTime.now().plusMinutes(15), epicTask);

        taskManager.createSubTask(subTaskOne);
        taskManager.createSubTask(subTaskTwo);
        taskManager.createSubTask(subTaskThree);

        assertEquals(Status.DONE, epicTask.getStatus());
    }

    @Test
    void subTaskWithStatusNewAndSubTaskWithStatusDoneShouldSetInProgressStatus() throws Exception {
        SubTask subTaskOne = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(1), LocalDateTime.now(), epicTask);
        SubTask subTaskTwo = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(1), LocalDateTime.now().plusMinutes(15), epicTask);

        taskManager.createSubTask(subTaskOne);
        taskManager.createSubTask(subTaskTwo);

        assertEquals(Status.IN_PROGRESS, epicTask.getStatus());
    }

    @Test
    void subTasksWithStatusInProgressShouldSetInProgressStatus() throws Exception {
        SubTask subTaskOne = new SubTask("name", "description", Status.IN_PROGRESS, Duration.ofMinutes(1), LocalDateTime.now(), epicTask);
        SubTask subTaskTwo = new SubTask("name", "description", Status.IN_PROGRESS, Duration.ofMinutes(1), LocalDateTime.now().plusMinutes(15), epicTask);

        taskManager.createSubTask(subTaskOne);
        taskManager.createSubTask(subTaskTwo);

        assertEquals(Status.IN_PROGRESS, epicTask.getStatus());
    }
}