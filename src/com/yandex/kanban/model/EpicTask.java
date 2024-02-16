package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yandex.kanban.service.FileBackedTasksManager.dateTimeFormatter;

public class EpicTask extends Task {
    private List<SubTask> subTasks;
    private final ArrayList<Integer> subtaskIds;
    private LocalDateTime endTime;

    public EpicTask(String name, String description, Status status) {
        super(name, description, status);
        this.typeOfTask = TypeOfTask.EPIC;
        this.subtaskIds = new ArrayList<>();
    }

    public EpicTask(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.typeOfTask = TypeOfTask.EPIC;
        this.subtaskIds = new ArrayList<>();
    }


    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }



    @Override
    public String toString() {
        return "Эпическая задача №" + id +
                ", имя = " + name +
                ", описание " + description +
                ", статус = " + status +
                ", продолжительность " + duration.toMinutes() +
                " мин, время начала " + startTime.format(dateTimeFormatter) +
                ", время окончания " + endTime +
                ", содержит подзадачи - " + getSubtaskIds();
    }
}