package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SimpleTask extends Task {
    public SimpleTask(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        typeOfTask = TypeOfTask.TASK;
    }

    public SimpleTask(int id, String name, String description, Status status, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime, LocalDateTime timeToEnd) {
        super(id, name, description, status, typeOfTask, duration, startTime);
    }

    @Override
    public String toString() {
        return "\nПростая задача №" + id +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status;
    }
}
