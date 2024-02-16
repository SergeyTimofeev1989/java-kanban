package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.yandex.kanban.service.FileBackedTasksManager.dateTimeFormatter;

public class SimpleTask extends Task {
    public SimpleTask(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.typeOfTask = TypeOfTask.TASK;
        this.endTime = getEndTime();
    }

    public SimpleTask(int id, String name, String description, Status status, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, typeOfTask, duration, startTime);
        this.endTime = getEndTime();
    }

    @Override
    public String toString() {
        return "Простая задача №" + id +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status +
                ", продолжительность " + duration.toMinutes() +
                " мин, время начала " + startTime.format(dateTimeFormatter) +
                ", время окончания " + endTime.format(dateTimeFormatter);
    }
}
