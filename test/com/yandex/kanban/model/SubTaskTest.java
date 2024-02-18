package com.yandex.kanban.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    @Test
    void getEpicTaskTest() {
        EpicTask epicTask = new EpicTask();
        SubTask subTask = new SubTask(epicTask);

        assertEquals(epicTask, subTask.getEpicTask(), "Не получилось получить эпик у подзадачи");
    }
}