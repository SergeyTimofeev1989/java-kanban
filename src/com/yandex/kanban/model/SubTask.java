package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.yandex.kanban.service.FileBackedTasksManager.dateTimeFormatter;

public class SubTask extends Task {
    private final EpicTask epicTask;


    public SubTask(String name, String description, Status status, Duration duration, LocalDateTime startTime, EpicTask epicTask) {
        super(name, description, status, duration, startTime);
        this.typeOfTask = TypeOfTask.SUBTASK;
        this.epicTask = epicTask;
        this.endTime = getEndTime();
    }

    public SubTask(int id, String name, String description, Status status, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime, EpicTask epicTask) {
        super(id, name, description, status, typeOfTask, duration, startTime);
        this.epicTask = epicTask;
        this.endTime = getEndTime();
    }


    public EpicTask getEpicTask() {
        return epicTask;
    }

    @Override
    public String toString() {
        return "Подзадача №" + id +
                ", принадлежит эпической задаче №" + epicTask.getId() +
                ", имя = " + name +
                ", описание " + description +
                ", статус = " + status +
                ", продолжительность " + duration.toMinutes() +
                " мин, время начала " + startTime.format(dateTimeFormatter) +
                ", время окончания " + endTime.format(dateTimeFormatter);
    }
}
