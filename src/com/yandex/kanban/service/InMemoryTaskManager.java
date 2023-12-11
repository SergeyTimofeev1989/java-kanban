package com.yandex.kanban.service;


import com.yandex.kanban.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    protected int id = 1;
    protected final HashMap<Integer, SimpleTask> packOfSimpleTasks = new HashMap<>();
    protected final HashMap<Integer, EpicTask> packOfEpicTasks = new HashMap<>();
    protected final HashMap<Integer, SubTask> packOfSubtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    
    @Override
    public ArrayList<SimpleTask> getPackOfSimpleTasks() {
        return new ArrayList<>(packOfSimpleTasks.values());
    }

    @Override
    public ArrayList<EpicTask> getPackOfEpicTasks() {
        return new ArrayList<>(packOfEpicTasks.values());
    }

    @Override
    public ArrayList<SubTask> getPackOfSubTasks() {
        return new ArrayList<>(packOfSubtasks.values());
    }

    @Override
    public void clearAllSimpleTasks() {
        packOfSimpleTasks.clear();
    }

    @Override
    public void clearAllEpicTasks() {
        packOfEpicTasks.clear();
        packOfSubtasks.clear();
    }

    @Override
    public void clearAllSubTasks() {
        packOfSubtasks.clear();
        for (EpicTask epic : packOfEpicTasks.values()) {
            epic.getSubtaskIds().clear();
            changeStatus(epic);
        }
    }

    @Override
    public SimpleTask getSimpleTaskById(int id) {
        final SimpleTask simpleTask = packOfSimpleTasks.get(id);
        historyManager.add(simpleTask);
        return simpleTask;
    }

    @Override
    public EpicTask getEpicTaskById(int id) {
        final EpicTask epicTask = packOfEpicTasks.get(id);
        historyManager.add(epicTask);
        return epicTask;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        final SubTask subTask = packOfSubtasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public int createSimpleTask(SimpleTask simpleTask) {
        simpleTask.setId(id++);
        packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
        return simpleTask.getId();
    }

    @Override
    public int createEpicTask(EpicTask epicTask) {
        epicTask.setId(id++);
        changeStatus(epicTask);
        packOfEpicTasks.put(epicTask.getId(), epicTask);
        return epicTask.getId();
    }

    @Override
    public int createSubTask(SubTask subTask) {
        subTask.setId(id++);
        packOfSubtasks.put(subTask.getId(), subTask);
        subTask.getEpicTask().getSubtaskIds().add(subTask.getId());
        changeStatus(subTask.getEpicTask());
        return subTask.getId();
    }

    @Override
    public int updateSimpleTask(SimpleTask simpleTask) {
        packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
        return simpleTask.getId();
    }

    @Override
    public int updateEpicTask(EpicTask epicTack) {
        packOfEpicTasks.put(epicTack.getId(), epicTack);
        return epicTack.getId();
    }

    @Override
    public int updateSubTask(SubTask subTask) {
        packOfSubtasks.put(subTask.getId(), subTask);
        EpicTask epicTask = packOfEpicTasks.get(subTask.getId());
        changeStatus(epicTask);
        return subTask.getId();
    }

    @Override
    public void deleteSimpleTaskById(int idOfSimpleTask) {
        packOfSimpleTasks.remove(idOfSimpleTask);
    }

    @Override
    public void deleteEpicTaskById(int idOfEpicTask) {
        packOfEpicTasks.get(idOfEpicTask).getSubtaskIds().clear();
        packOfEpicTasks.remove(idOfEpicTask);
    }

    @Override
    public void deleteSubTaskById(int idOfSubtask) {
        EpicTask epicTask = packOfSubtasks.get(idOfSubtask).getEpicTask();
        epicTask.getSubtaskIds().remove(idOfSubtask);
        packOfSubtasks.remove(idOfSubtask);
        changeStatus(epicTask);
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(int epicId) {
        ArrayList<SubTask> listForReturn = new ArrayList<>();
        EpicTask epicTask = packOfEpicTasks.get(epicId);
        for (Integer id : epicTask.getSubtaskIds()) {
            listForReturn.add(packOfSubtasks.get(id));
        }
        return listForReturn;
    }

    @Override
    public List<Task> history() {
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
}

