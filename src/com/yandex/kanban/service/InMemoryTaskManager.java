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
    protected List<Task> viewedTask = new ArrayList<>();

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
        if (viewedTask.size() != 10) {
            viewedTask.add(packOfSimpleTasks.get(id));
        } else {
            viewedTask.remove(0);
            viewedTask.add(packOfSimpleTasks.get(id));
        }
        return packOfSimpleTasks.get(id);

    }

    @Override
    public EpicTask getEpicTaskById(int id) {
        if (viewedTask.size() != 10) {
            viewedTask.add(packOfEpicTasks.get(id));
        } else {
            viewedTask.remove(0);
            viewedTask.add(packOfEpicTasks.get(id));
        }
        return packOfEpicTasks.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        if (viewedTask.size() != 10) {
            viewedTask.add(packOfSubtasks.get(id));
        } else {
            viewedTask.remove(0);
            viewedTask.add(packOfSubtasks.get(id));
        }
        return packOfSubtasks.get(id);
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
        /* на 109 строке исключение выходило, потому что в Main на 16 строке в конструктор класса я не добавлял epicTask
        Я добавил epicTask. Теперь getEpicTask() не ссылается на null и у меня возник вопрос,
        а нужен ли вообще в SubTask конструктор на 6й строке? Думаю его можно удалить.
        */
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

