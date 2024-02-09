package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EpicTask extends Task {
    private final ArrayList<Integer> subtaskIds;

    public EpicTask(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        typeOfTask = TypeOfTask.EPIC;
        subtaskIds = new ArrayList<>();
    }

    public EpicTask(int id, String name, String description, Status status, TypeOfTask type, Duration duration, LocalDateTime startTime, LocalDateTime timeToEnd) {
        super(name, description, status, duration, startTime);
        typeOfTask = TypeOfTask.EPIC;
        subtaskIds = new ArrayList<>();
    }


    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }


    @Override
    public String toString() {
        return "\n" +
                "Эпическая задача №" + id +
                ", в ней есть подзадачи " + subtaskIds +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status;
    }
}