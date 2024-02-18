package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;



public class SimpleTask extends Task {
    public SimpleTask(String name, String description, Status status) {
        super(name, description, status);
        typeOfTask = TypeOfTask.TASK;
    }

    public SimpleTask() {
    }

    public SimpleTask(int id, String name, String description, Status status, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, typeOfTask, duration, startTime);
        this.typeOfTask = TypeOfTask.TASK;
        this.endTime = startTime.plus(duration);
    }

    public SimpleTask(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.typeOfTask = TypeOfTask.TASK;
        this.endTime = startTime.plus(duration);
    }

    public SimpleTask(int id, String name, String description, Status status, TypeOfTask typeOfTask) {
        super(id, name, description, status);
    }

    @Override
    public String toString() {
        return "Простая задача №" + id +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status +
                ", продолжительность " + duration.toMinutes() +
                " мин, начало в " + startTime +
                ", конец в " + endTime;
    }
}
