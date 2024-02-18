package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;



public class SubTask extends Task {
    private final EpicTask epicTask;

    public SubTask(EpicTask epicTask) {
        this.epicTask = epicTask;
    }

    public SubTask(String name, String description, Status status, EpicTask epicTask) {
        super(name, description, status);
        typeOfTask = TypeOfTask.SUBTASK;
        this.epicTask = epicTask;
    }

    public SubTask(int id, Status status, EpicTask epicTask) {
        super(id, status);
        this.typeOfTask = TypeOfTask.SUBTASK;
        this.epicTask = epicTask;
    }

    public SubTask(String name, String description, Status status, Duration duration, LocalDateTime startTime, EpicTask epicTask) {
        super(name, description, status, duration, startTime);
        typeOfTask = TypeOfTask.SUBTASK;
        this.epicTask = epicTask;
        this.endTime = startTime.plus(duration);
    }

    public SubTask(int id, String name, String description, Status status, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime, EpicTask epicTask) {
        super(id, name, description, status, typeOfTask, duration, startTime);
        this.epicTask = epicTask;
        this.endTime = startTime.plus(duration);
    }

    public SubTask(String name, String description, Status status, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime, EpicTask epicTask) {
        super(name, description, status, typeOfTask, duration, startTime);
        this.epicTask = epicTask;
        this.endTime = startTime.plus(duration);
    }

    public SubTask(int id, String name, String description, Status status, TypeOfTask typeOfTask, EpicTask epicTask) {
        super(id, name, description, status);
        this.epicTask = epicTask;
    }


    public EpicTask getEpicTask() {
        return epicTask;
    }

    @Override
    public String toString() {
        return "Подзадача задача №" + id +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status +
                ", продолжительность " + duration.toMinutes() +
                " мин, начало в " + startTime +
                ", конец в " + endTime +
                ", принадлежит к эпику № - " + epicTask.getId();
    }

}
