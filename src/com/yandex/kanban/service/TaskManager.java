package com.yandex.kanban.service;

import com.yandex.kanban.model.EpicTask;
import com.yandex.kanban.model.SimpleTask;
import com.yandex.kanban.model.SubTask;
import com.yandex.kanban.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    ArrayList<SimpleTask> getPackOfSimpleTasks();

    ArrayList<EpicTask> getPackOfEpicTasks();

    ArrayList<SubTask> getPackOfSubTasks();


    void clearAllSimpleTasks();

    void clearAllEpicTasks();

    void clearAllSubTasks();


    SimpleTask getSimpleTaskById(int id);

    EpicTask getEpicTaskById(int id);

    SubTask getSubTaskById(int id);


    int createSimpleTask(SimpleTask simpleTask) throws Exception;

    int createEpicTask(EpicTask epicTask);

    int createSubTask(SubTask subTask) throws Exception;


    int updateSimpleTask(SimpleTask simpleTask) throws Exception;

    int updateEpicTask(EpicTask epicTack);

    int updateSubTask(SubTask subTask) throws Exception;


    void deleteSimpleTaskById(int idOfSimpleTask);

    void deleteEpicTaskById(int idOfEpicTask);

    void deleteSubTaskById(int idOfSubtask);


    List<SubTask> getEpicSubtasks(int epicId);

    List<Task> getHistory();

}
