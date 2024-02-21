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
    private final TreeSet<Task> prioritizedTasks;

    public InMemoryTaskManager() {
        prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())));
    }


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
        for (SimpleTask simpleTask : packOfSimpleTasks.values()) {
            historyManager.remove(simpleTask.getId());
            prioritizedTasks.remove(simpleTask);
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
        for (SubTask subTask : packOfSubtasks.values()) {
            historyManager.remove(subTask.getId());
            prioritizedTasks.remove(subTask);
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
    public int createSimpleTask(SimpleTask simpleTask) throws Exception {
        simpleTask.setId(id++);
        if (isTimeNotOverlap(simpleTask)) {
            addTask(simpleTask);
            packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
        } else {
            throw new Exception("Время пересекается, операция прервана");
        }
        return simpleTask.getId();
    }

    // Создание эпической задачи
    @Override
    public int createEpicTask(EpicTask epicTask) {
        epicTask.setId(id++);
        changeStatus(epicTask);
        updateTaskTimes(epicTask);
        packOfEpicTasks.put(epicTask.getId(), epicTask);
        return epicTask.getId();
    }

    // Создание подзадачи
    @Override
    public int createSubTask(SubTask subTask) throws Exception {
        subTask.setId(id++);
        if (isTimeNotOverlap(subTask)) {
            addTask(subTask);
            packOfSubtasks.put(subTask.getId(), subTask);
            subTask.getEpicTask().getSubtaskIds().add(subTask.getId());
            changeStatus(subTask.getEpicTask());
            updateTaskTimes(subTask.getEpicTask());
        } else {
            throw new Exception("Время пересекается, операция прервана");
        }
        return subTask.getId();
    }

    // Обновление простой задачи
    @Override
    public int updateSimpleTask(SimpleTask simpleTask) throws Exception {
        if (isTimeNotOverlap(simpleTask)) {
            prioritizedTasks.remove(simpleTask);
            addTask(simpleTask);
            packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
        } else {
            throw new Exception("Время пересекается, операция прервана");
        }
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
    public int updateSubTask(SubTask subTask) throws Exception {
        if (isTimeNotOverlap(subTask)) {
            prioritizedTasks.remove(subTask);
            addTask(subTask);
            packOfSubtasks.put(subTask.getId(), subTask);
            EpicTask epicTask = packOfEpicTasks.get(subTask.getId());
            changeStatus(epicTask);
            updateTaskTimes(epicTask);
        } else {
            throw new Exception("Время пересекается, операция прервана");
        }
        return subTask.getId();
    }

    // Удаление простой задачи по ID
    @Override
    public void deleteSimpleTaskById(int idOfSimpleTask) {
        SimpleTask simpleTask = packOfSimpleTasks.remove(idOfSimpleTask);
        prioritizedTasks.remove(simpleTask);
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
        SubTask subTask = packOfSubtasks.remove(idOfSubtask);
        prioritizedTasks.remove(subTask);
        changeStatus(epicTask);
        updateTaskTimes(epicTask);
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


    protected void updateTaskTimes(EpicTask task) {
        // Изменение времени начала
        Optional<LocalDateTime> minStartTime = packOfSubtasks.values().stream()
                .filter(subTask -> task.getSubtaskIds().contains(subTask.getId()))
                .map(SubTask::getStartTime)
                .min(Comparator.nullsLast(Comparator.naturalOrder()));
        LocalDateTime time = minStartTime.orElse(null);
        task.setStartTime(time);

        // Изменение продолжительности
        long minutes = packOfSubtasks.values().stream()
                .filter(subTask -> task.getSubtaskIds().contains(subTask.getId()))
                .mapToLong(subTask -> subTask.getDuration().toMinutes())
                .sum();
        task.setDuration(Duration.ofMinutes(minutes));

        // Изменение времени окончания
        Optional<LocalDateTime> latestEndTime = task.getSubtaskIds().stream()
                .map(packOfSubtasks::get)
                .map(SubTask::getEndTime)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo);
        latestEndTime.ifPresent(task::setEndTime);
    }

    public void addTask(Task task) {
        prioritizedTasks.add(task);
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    public boolean isTimeNotOverlap(Task task) {
        return getPrioritizedTasks().stream()
                .filter(element -> element.getStartTime() != null && element.getDuration() != null)
                .noneMatch(element -> task.getStartTime().isBefore(element.getEndTime()) && task.getEndTime().isAfter(element.getStartTime()));
    }
}

