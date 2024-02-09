package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {
    private final EpicTask epicTask;

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


    public EpicTask getEpicTask() {
        return epicTask;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicTask=" + epicTask +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", typeOfTask=" + typeOfTask +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
