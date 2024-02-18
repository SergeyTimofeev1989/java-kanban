package com.yandex.kanban.service;

import com.yandex.kanban.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {
    public T taskManager;

    @Test
    void clearAllSimpleTasks() {
        SimpleTask simpleTask = new SimpleTask();
        taskManager.createSimpleTask(simpleTask);
        taskManager.clearAllSimpleTasks();

        assertEquals(0, taskManager.getPackOfSimpleTasks().size(), "Неверное количество задач");
    }

    @Test
    void clearAllEpicTasks() {
        EpicTask epicTask = new EpicTask();
        taskManager.createEpicTask(epicTask);
        taskManager.clearAllEpicTasks();

        assertEquals(0, taskManager.getPackOfEpicTasks().size(), "Неверное количество задач");
    }

    @Test
    void clearAllSubTasks() {
        EpicTask epicTask = new EpicTask();
        SubTask subTask = new SubTask(3, "name", "description", Status.NEW, TypeOfTask.SUBTASK, Duration.ofMinutes(10), LocalDateTime.now().plusHours(2), epicTask);
        taskManager.createSubTask(subTask);
        taskManager.clearAllSubTasks();

        assertEquals(0, taskManager.getPackOfSubTasks().size(), "Неверное количество задач");
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void getSimpleTaskByIdTest(int id) {
        SimpleTask simpleTaskOne = new SimpleTask();
        SimpleTask simpleTaskTwo = new SimpleTask();
        SimpleTask simpleTaskThree = new SimpleTask();
        taskManager.createSimpleTask(simpleTaskOne);
        taskManager.createSimpleTask(simpleTaskTwo);
        taskManager.createSimpleTask(simpleTaskThree);


        assertEquals(id, taskManager.getSimpleTaskById(id).getId(), "Неверный id");
        assertEquals(id, taskManager.getSimpleTaskById(id).getId(), "Неверный id");
        assertEquals(id, taskManager.getSimpleTaskById(id).getId(), "Неверный id");
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void getEpicTaskByIdTest(int id) {
        EpicTask epicTaskOne = new EpicTask();
        EpicTask epicTaskTwo = new EpicTask();
        EpicTask epicTaskThree = new EpicTask();
        taskManager.createEpicTask(epicTaskOne);
        taskManager.createEpicTask(epicTaskTwo);
        taskManager.createEpicTask(epicTaskThree);

        assertEquals(id, taskManager.getEpicTaskById(id).getId());
        assertEquals(id, taskManager.getEpicTaskById(id).getId());
        assertEquals(id, taskManager.getEpicTaskById(id).getId());
    }


    @Test
    void createSimpleTaskTest() {
        SimpleTask simpleTask = new SimpleTask("name", "description", Status.NEW);
        final int taskId = taskManager.createSimpleTask(simpleTask);
        final Task savedTask = taskManager.getSimpleTaskById(taskId);

        assertEquals(SimpleTask.class, simpleTask.getClass());
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(simpleTask, savedTask, "Задачи не совпадают");

        final List<SimpleTask> tasks = taskManager.getPackOfSimpleTasks();


        assertNotNull(tasks, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Неверное количество задач");
        assertEquals(simpleTask, tasks.get(0), "Задачи не совпадают");
    }

    @Test
    void createEpicTaskTest() {
        EpicTask epicTask = new EpicTask("name", "description", Status.NEW);
        final int taskId = taskManager.createEpicTask(epicTask);
        final Task savedTask = taskManager.getEpicTaskById(taskId);

        assertEquals(EpicTask.class, epicTask.getClass());
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(epicTask, savedTask, "Задачи не совпадают");

        final List<EpicTask> tasks = taskManager.getPackOfEpicTasks();

        assertNotNull(tasks, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Неверное количество задач");
        assertEquals(epicTask, tasks.get(0), "Задачи не совпадают");
    }

    @Test
    void createSubTaskTest() {
        EpicTask epicTask = new EpicTask("name", "description", Status.NEW);
        SubTask subTask = new SubTask(3, "name", "description", Status.NEW, TypeOfTask.SUBTASK, Duration.ofMinutes(10), LocalDateTime.now().plusHours(2), epicTask);
        final int taskId = taskManager.createSubTask(subTask);
        final Task savedTask = taskManager.getSubTaskById(taskId);

        assertEquals(SubTask.class, subTask.getClass());
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(subTask, savedTask, "Задачи не совпадают");

        final List<SubTask> tasks = taskManager.getPackOfSubTasks();

        assertNotNull(tasks, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Неверное количество задач");
        assertEquals(subTask, tasks.get(0), "Задачи не совпадают");
    }

    @Test
    void updateSimpleTaskTest() {
        SimpleTask simpleTask = new SimpleTask("name", "description", Status.NEW);
        final int taskId = taskManager.createSimpleTask(simpleTask);
        final Task savedTask = taskManager.getSimpleTaskById(taskId);

        assertEquals(SimpleTask.class, simpleTask.getClass());
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(simpleTask, savedTask, "Задачи не совпадают");

        final List<SimpleTask> tasks = taskManager.getPackOfSimpleTasks();


        assertNotNull(tasks, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Неверное количество задач");
        assertEquals(simpleTask, tasks.get(0), "Задачи не совпадают");
    }

    @Test
    void updateEpicTaskTest() {
        EpicTask epicTask = new EpicTask("name", "description", Status.NEW);
        final int taskId = taskManager.createEpicTask(epicTask);
        final Task savedTask = taskManager.getEpicTaskById(taskId);

        assertEquals(EpicTask.class, epicTask.getClass());
        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(epicTask, savedTask, "Задачи не совпадают");

        final List<EpicTask> tasks = taskManager.getPackOfEpicTasks();

        assertNotNull(tasks, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Неверное количество задач");
        assertEquals(epicTask, tasks.get(0), "Задачи не совпадают");
    }

    @ParameterizedTest
    @ValueSource(ints = 1)
    void deleteSimpleTaskById() {
        SimpleTask simpleTask = new SimpleTask("name", "description", Status.NEW);

        taskManager.createSimpleTask(simpleTask);
        int size = taskManager.getPackOfSimpleTasks().size();
        taskManager.deleteSimpleTaskById(1);

        assertEquals(size - 1, taskManager.getPackOfSimpleTasks().size(), "размер списка не изменился, возможно задача не удалилась");
    }

    @ParameterizedTest
    @ValueSource(ints = 1)
    void deleteEpicTaskById() {
        EpicTask epicTask = new EpicTask("name", "description", Status.NEW);
        taskManager.createEpicTask(epicTask);
        int size = taskManager.getPackOfEpicTasks().size();
        taskManager.deleteEpicTaskById(1);

        assertEquals(size - 1, taskManager.getPackOfEpicTasks().size(), "размер списка не изменился, возможно задача не удалилась");
    }

    @ParameterizedTest
    @ValueSource(ints = 1)
    void deleteSubTaskById(int id) {
        EpicTask epicTask = new EpicTask("name", "description", Status.NEW);
        SubTask subTask = new SubTask(3, "name", "description", Status.NEW, TypeOfTask.SUBTASK, Duration.ofMinutes(10), LocalDateTime.now().plusHours(2), epicTask);
        taskManager.createEpicTask(epicTask);
        taskManager.createSubTask(subTask);
        int size = taskManager.getPackOfSubTasks().size();
        taskManager.deleteSimpleTaskById(id);

        assertEquals(size - 1, taskManager.getPackOfSimpleTasks().size());
    }

    @ParameterizedTest
    @ValueSource(ints = 1)
    void getEpicSubTasks(int id) {
        EpicTask epicTask = new EpicTask("name", "description", Status.NEW);
        SubTask subTask = new SubTask(3, "name", "description", Status.NEW, TypeOfTask.SUBTASK, Duration.ofMinutes(10), LocalDateTime.now().plusHours(2), epicTask);
        taskManager.createEpicTask(epicTask);
        taskManager.createSubTask(subTask);
        List<SubTask> list = taskManager.getEpicSubtasks(id);

        assertEquals(ArrayList.class, list.getClass());
    }

    @Test
    void getHistoryTest() {

        assertEquals(ArrayList.class, taskManager.getHistory().getClass());
    }

    @Test
    void isSubTaskHasEpicTask() {
        EpicTask epicTask = new EpicTask();
        taskManager.createEpicTask(epicTask);
        SubTask subTask = new SubTask("name", "description", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now(), epicTask);
        taskManager.createSubTask(subTask);

        assertEquals(epicTask, subTask.getEpicTask());
    }
}
