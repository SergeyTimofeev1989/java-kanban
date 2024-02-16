package com.yandex.kanban.service;


import com.yandex.kanban.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    protected int id = 1;
    protected final HashMap<Integer, SimpleTask> packOfSimpleTasks = new HashMap<>();
    protected final HashMap<Integer, EpicTask> packOfEpicTasks = new HashMap<>();
    protected final HashMap<Integer, SubTask> packOfSubtasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();


    // Получение списка всех простых задач
    @Override
    public ArrayList<SimpleTask> getPackOfSimpleTasks() {
        return new ArrayList<>(packOfSimpleTasks.values());
    }

    // Получение списка всех эпических задач
    @Override
    public ArrayList<EpicTask> getPackOfEpicTasks() {
        return new ArrayList<>(packOfEpicTasks.values());
    }

    // Получение списка всех подзадач
    @Override
    public ArrayList<SubTask> getPackOfSubTasks() {
        return new ArrayList<>(packOfSubtasks.values());
    }

    // Удаление списка всех простых задач
    @Override
    public void clearAllSimpleTasks() {
        for (SimpleTask value : packOfSimpleTasks.values()) {
            historyManager.remove(value.getId());
        }
        packOfSimpleTasks.clear();
    }

    // Удаление списка всех эпических задач
    @Override
    public void clearAllEpicTasks() {
        for (Task task : packOfEpicTasks.values()) {
            historyManager.remove(task.getId());
        }
        packOfEpicTasks.clear();
        for (Task task : packOfSubtasks.values()) {
            historyManager.remove(task.getId());
        }
        packOfSubtasks.clear();
    }

    // Удаление списка всех подзадач
    @Override
    public void clearAllSubTasks() {
        for (Task task : packOfSubtasks.values()) {
            historyManager.remove(task.getId());
        }
        packOfSubtasks.clear();
        for (EpicTask epic : packOfEpicTasks.values()) {
            epic.getSubtaskIds().clear();
            changeStatus(epic);
        }
    }

    // Получение простой задачи по ID
    @Override
    public SimpleTask getSimpleTaskById(int id) {
        final SimpleTask simpleTask = packOfSimpleTasks.get(id);
        historyManager.add(simpleTask);
        return simpleTask;
    }

    // Получение эпической задачи по ID
    @Override
    public EpicTask getEpicTaskById(int id) {
        final EpicTask epicTask = packOfEpicTasks.get(id);
        historyManager.add(epicTask);
        return epicTask;
    }

    // Получение подзадачи по ID
    @Override
    public SubTask getSubTaskById(int id) {
        final SubTask subTask = packOfSubtasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    // Создание простой задачи
    @Override
    public int createSimpleTask(SimpleTask simpleTask) {
        simpleTask.setId(id++);
        packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
        return simpleTask.getId();
    }

    // Создание эпической задачи
    @Override
    public int createEpicTask(EpicTask epicTask) {
        epicTask.setId(id++);
        changeStatus(epicTask);
        changeStartTime(epicTask);
        changeDuration(epicTask);
        changeEndTime(epicTask);
        packOfEpicTasks.put(epicTask.getId(), epicTask);
        return epicTask.getId();
    }

    // Создание подзадачи
    @Override
    public int createSubTask(SubTask subTask) {
        if (packOfEpicTasks.containsValue(subTask.getEpicTask())) {
            EpicTask epicTask = packOfEpicTasks.get(subTask.getEpicTask());
            subTask.setId(id++);
            packOfSubtasks.put(subTask.getId(), subTask);
            subTask.getEpicTask().getSubtaskIds().add(subTask.getId());
            changeStatus(subTask.getEpicTask());
            changeStartTime(epicTask);
            changeDuration(epicTask);
            changeEndTime(epicTask);
        }
        return subTask.getId();
    }


    // Обновление простой задачи
    @Override
    public int updateSimpleTask(SimpleTask simpleTask) {
        packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
        return simpleTask.getId();
    }

    // Обновление эпической задачи
    @Override
    public int updateEpicTask(EpicTask epicTask) {
        packOfEpicTasks.put(epicTask.getId(), epicTask);
        return epicTask.getId();
    }

    // Обновление подзадачи
    @Override
    public int updateSubTask(SubTask subTask) {
        packOfSubtasks.put(subTask.getId(), subTask);
        EpicTask epicTask = packOfEpicTasks.get(subTask.getId());
        changeStatus(epicTask);
        changeStartTime(epicTask);
        changeDuration(epicTask);
        changeEndTime(epicTask);
        return subTask.getId();
    }

    // Удаление простой задачи по ID
    @Override
    public void deleteSimpleTaskById(int idOfSimpleTask) {
        packOfSimpleTasks.remove(idOfSimpleTask);
        historyManager.remove(idOfSimpleTask);
    }

    // Удаление эпической задачи по ID
    @Override
    public void deleteEpicTaskById(int idOfEpicTask) {
        packOfEpicTasks.get(idOfEpicTask).getSubtaskIds().clear();
        EpicTask epic = packOfEpicTasks.remove(idOfEpicTask);
        if (epic == null) {
            return;
        }
        historyManager.remove(idOfEpicTask);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            packOfSubtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
    }

    // Удаление подзадачи по ID
    @Override
    public void deleteSubTaskById(int idOfSubtask) {
        EpicTask epicTask = packOfSubtasks.get(idOfSubtask).getEpicTask();
        epicTask.getSubtaskIds().remove(idOfSubtask);
        packOfSubtasks.remove(idOfSubtask);
        changeStatus(epicTask);
        changeStartTime(epicTask);
        changeDuration(epicTask);
        changeEndTime(epicTask);
        historyManager.remove(idOfSubtask);
    }

    // Получение списка всех подзадач определённого эпика
    @Override
    public ArrayList<SubTask> getEpicSubtasks(int epicId) {
        ArrayList<SubTask> listForReturn = new ArrayList<>();
        EpicTask epicTask = packOfEpicTasks.get(epicId);
        for (Integer id : epicTask.getSubtaskIds()) {
            listForReturn.add(packOfSubtasks.get(id));
        }
        return listForReturn;
    }

    //
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    public void changeStatus(EpicTask task) {
        int countOfNewStatus = 0;
        int countOfDoneStatus = 0;
        if (task.getSubtaskIds().isEmpty()) {
            task.setStatus(Status.NEW);
            return;
        } else {
            for (SubTask subTask : packOfSubtasks.values()) {
                if (Status.NEW.equals(subTask.getStatus())) {
                    countOfNewStatus++;
                } else if (Status.DONE.equals(subTask.getStatus())) {
                    countOfDoneStatus++;
                }
            }
        }
        if (countOfNewStatus == task.getSubtaskIds().size()) {
            task.setStatus(Status.NEW);
        } else if (countOfDoneStatus == task.getSubtaskIds().size()) {
            task.setStatus(Status.DONE);
        } else {
            task.setStatus(Status.IN_PROGRESS);
        }
    }


    public void changeStartTime(EpicTask task) {
        if (packOfSubtasks != null && task != null && task.getSubtaskIds() != null) {
            Optional<LocalDateTime> earliestStartTime = packOfSubtasks.entrySet()
                    .stream()
                    .filter(entry -> task.getSubtaskIds().contains(entry.getKey()))
                    .map(entry -> entry.getValue().getStartTime())
                    .min(LocalDateTime::compareTo);

            if (earliestStartTime.isPresent()) {
                task.setStartTime(earliestStartTime.get());
            } else {
                task.setStartTime(LocalDateTime.of(2050, 1, 1, 1, 1));
            }
        } else {
            task.setStartTime(LocalDateTime.now());
        }
    }

    public void changeDuration(EpicTask task) {
        Optional<Duration> totalDuration = packOfSubtasks.entrySet()
                .stream()
                .filter(entry -> task.getSubtaskIds().contains(entry.getKey()))
                .map(entry -> entry.getValue().getDuration())
                .reduce((dt1, dt2) -> dt1.plusMinutes(dt2.toMinutes()));
        if (totalDuration.isPresent()) {
            task.setDuration(totalDuration.get());
        } else {
            task.setDuration(Duration.ofMinutes(0));
        }
    }


    public void changeEndTime(EpicTask task) {
        Optional<LocalDateTime> latestEndTime = packOfSubtasks.entrySet()
                .stream()
                .filter(entry -> task.getSubtaskIds().contains(entry.getKey()))
                .map(entry -> entry.getValue().getStartTime().plus(entry.getValue().getDuration()))
                .max(LocalDateTime::compareTo);

        if (latestEndTime.isPresent()) {
            task.setEndTime(latestEndTime.get());
        } else {
            task.setEndTime(LocalDateTime.of(2050, 1, 1, 1, 1));
        }
    }
}

