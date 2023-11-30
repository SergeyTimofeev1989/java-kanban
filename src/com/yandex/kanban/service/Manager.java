package com.yandex.kanban.service;


import com.yandex.kanban.model.EpicTask;
import com.yandex.kanban.model.SimpleTask;
import com.yandex.kanban.model.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Manager {
    protected final HashMap<Integer, SimpleTask> packOfSimpleTasks = new HashMap<>();
    protected final HashMap<Integer, EpicTask> packOfEpicTasks = new HashMap<>();
    protected final HashMap<Integer, SubTask> packOfSubtasks = new HashMap<>();
    protected int NextId = 1;


    public ArrayList<SimpleTask> getPackOfSimpleTasks() {
        return new ArrayList<>(packOfSimpleTasks.values());
    }

    public ArrayList<EpicTask> getPackOfEpicTasks() {
        return new ArrayList<>(packOfEpicTasks.values());
    }

    public ArrayList<SubTask> getPackOfSubTasks() {
        return new ArrayList<>(packOfSubtasks.values());
    }


    public void clearAllSimpleTasks() {
        packOfSimpleTasks.clear();
    }

    public void clearAllEpicTasks() {
        packOfEpicTasks.clear();
    }

    public void clearAllSubTasks() {
        packOfEpicTasks.clear();
        packOfSubtasks.clear();
    }


    public SimpleTask getSimpleTaskById(int id) {
        return packOfSimpleTasks.get(id);
    }

    public EpicTask getEpicTaskById(int id) {
        return packOfEpicTasks.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return packOfSubtasks.get(id);
    }


    public int createSimpleTask(SimpleTask simpleTask) {
        simpleTask.setId(NextId++);
        packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
        return simpleTask.getId();
    }

    public int createEpicTask(EpicTask epicTask) {
        epicTask.setId(NextId++);
        changeStatus(epicTask);
        packOfEpicTasks.put(epicTask.getId(), epicTask);
        return epicTask.getId();
    }

    public int createSubTask(SubTask subTask) {
        subTask.setId(NextId++);
        changeStatus(packOfEpicTasks.get(subTask.getId()));
        packOfSubtasks.put(subTask.getId(), subTask);
        subTask.getEpicTask().getSubTasks().add(subTask);
        subTask.getEpicTask().getSubtaskIds().add(subTask.getId());
        return subTask.getId();
    }


    public int updateSimpleTask(SimpleTask simpleTask) {
        packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
        return simpleTask.getId();
    }

    public int updateEpicTask(EpicTask epicTack) {
        packOfEpicTasks.put(epicTack.getId(), epicTack);
        return epicTack.getId();
    }

    public int updateSubTask(SubTask subTask) {
        packOfSubtasks.put(subTask.getId(), subTask);
        EpicTask epicTask = packOfEpicTasks.get(subTask.getId());
        return subTask.getId();
    }


    public void deleteSimpleTaskById(int idOfSimpleTask) {
        packOfSimpleTasks.remove(idOfSimpleTask);
    }

    public void deleteEpicTaskById(int idOfEpicTask) {
        packOfEpicTasks.get(idOfEpicTask).getSubtaskIds().clear();
        packOfEpicTasks.remove(idOfEpicTask);
    }

    public void deleteSubTaskById(int idOfSubtask) {
        EpicTask epicTask = packOfSubtasks.get(idOfSubtask).getEpicTask();
        epicTask.getSubtaskIds().remove(idOfSubtask);
        epicTask.getSubTasks().remove(idOfSubtask);
        packOfSubtasks.remove(idOfSubtask);
    }


    public ArrayList<SubTask> getEpicSubtasks(int epicId) {
        EpicTask epicTask = packOfEpicTasks.get(epicId);
        return epicTask.getSubTasks();
    }

    public void changeStatus(EpicTask task) {
        int countOfNewStatus = 0;
        int countOfDoneStatus = 0;
        if (task.getStatus().isEmpty()) {
            task.setStatus("NEW");
            return;
        } else {
            for (SubTask subTask : packOfSubtasks.values()) {
                if (subTask.getStatus().equals("NEW")) {
                    countOfNewStatus++;
                } else if (subTask.getStatus().equals("DONE")) {
                    countOfDoneStatus++;
                }
            }
        }
        if (countOfNewStatus == task.getSubtaskIds().size()) {
            task.setStatus("NEW");
        } else if (countOfDoneStatus == task.getSubtaskIds().size()) {
            task.setStatus("DONE");
        } else {
            task.setStatus("IN_PROGRESS");
        }
    }
}

