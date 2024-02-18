package com.yandex.kanban.service;

import com.yandex.kanban.model.EpicTask;
import com.yandex.kanban.model.SimpleTask;
import com.yandex.kanban.model.Status;
import com.yandex.kanban.model.SubTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackendTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    void setUp() {
        taskManager = new FileBackedTasksManager();
    }

    @Test
    void toStringTest() {
        SimpleTask simpleTask = new SimpleTask();
        EpicTask epicTask = new EpicTask();
        SubTask subTask = new SubTask("name", "description", Status.DONE, Duration.ofMinutes(10), LocalDateTime.now().plusHours(1), epicTask);

        assertEquals(8, taskManager.toString(simpleTask).split(",").length, "Сохраняются не все поля задачи");
        assertEquals(8, taskManager.toString(epicTask).split(",").length, "Сохраняются не все поля эпика");
        assertEquals(9, taskManager.toString(subTask).split(",").length, "Сохраняются не все поля подзадачи");
    }
}