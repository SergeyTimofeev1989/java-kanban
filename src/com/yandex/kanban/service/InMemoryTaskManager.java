package com.yandex.kanban.service;


import com.yandex.kanban.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {
    protected int id = 1;
    protected final HashMap<Integer, SimpleTask> packOfSimpleTasks = new HashMap<>();
    protected final HashMap<Integer, EpicTask> packOfEpicTasks = new HashMap<>();
    protected final HashMap<Integer, SubTask> packOfSubtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();


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
        for(Map.Entry<Integer, SimpleTask> entry : packOfSimpleTasks.entrySet()) {
            historyManager.getHistory().remove(entry);
        }
        packOfSimpleTasks.clear();
    }

    // Удаление списка всех эпических задач
    @Override
    public void clearAllEpicTasks() {
        for(Map.Entry<Integer, EpicTask> entry : packOfEpicTasks.entrySet()) {
            historyManager.getHistory().remove(entry);
        }
        packOfEpicTasks.clear();
        packOfSubtasks.clear();
    }

    // Удаление списка всех подзадач
    @Override
    public void clearAllSubTasks() {
        for(Map.Entry<Integer,SubTask> entry : packOfSubtasks.entrySet()) {
            historyManager.getHistory().remove(entry);
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
        packOfEpicTasks.put(epicTask.getId(), epicTask);
        return epicTask.getId();
    }

    // Создание подзадачи
    @Override
    public int createSubTask(SubTask subTask) {
        subTask.setId(id++);
        packOfSubtasks.put(subTask.getId(), subTask);
        subTask.getEpicTask().getSubtaskIds().add(subTask.getId());
        changeStatus(subTask.getEpicTask());
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
    public int updateEpicTask(EpicTask epicTack) {
        packOfEpicTasks.put(epicTack.getId(), epicTack);
        return epicTack.getId();
    }

    // Обновление подзадачи
    @Override
    public int updateSubTask(SubTask subTask) {
        packOfSubtasks.put(subTask.getId(), subTask);
        EpicTask epicTask = packOfEpicTasks.get(subTask.getId());
        changeStatus(epicTask);
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
        for(Map.Entry<Integer, SubTask> entry : packOfSubtasks.entrySet()) {
            if (entry.getValue().getEpicTask().getId() == idOfEpicTask) {
                entry.getValue().getEpicTask().getSubtaskIds().remove(idOfEpicTask);
            }
        }
        packOfEpicTasks.remove(idOfEpicTask);
        historyManager.remove(idOfEpicTask);
    }

    // Удаление подзадачи по ID
    @Override
    public void deleteSubTaskById(int idOfSubtask) {
        EpicTask epicTask = packOfSubtasks.get(idOfSubtask).getEpicTask();
        epicTask.getSubtaskIds().remove(idOfSubtask);
        packOfSubtasks.remove(idOfSubtask);
        changeStatus(epicTask);
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

    // Должен возвращать последние 10 просмотренных задач. Просмотром будем считаться вызов у менеджера методов получения задачи по идентификатору
    public HistoryManager getHistoryManager() {
        return historyManager;
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
}

