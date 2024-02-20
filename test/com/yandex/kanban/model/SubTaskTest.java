package com.yandex.kanban.model;

import com.yandex.kanban.service.InMemoryTaskManager;
import com.yandex.kanban.service.TaskManager;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    TaskManager taskManager = new InMemoryTaskManager();

    @Test
    void getEpicTaskTest() {
        EpicTask epicTask = new EpicTask();
        SubTask subTask = new SubTask(epicTask);

        assertEquals(epicTask, subTask.getEpicTask(), "Не получилось получить эпик у подзадачи");
    }

    @Test
    void isSubTaskHasEpicTask() throws Exception {
        EpicTask epicTask = new EpicTask();
        taskManager.createEpicTask(epicTask);
        SubTask subTask = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now(), epicTask);
        taskManager.createSubTask(subTask);

        assertEquals(epicTask, subTask.getEpicTask());
    }
}