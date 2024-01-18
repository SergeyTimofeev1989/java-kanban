package com.yandex.kanban.model;

import java.util.ArrayList;

public class EpicTask extends Task {
    private final ArrayList<Integer> subtaskIds;

    public EpicTask(String name, String description, Status status) {
        super(name, description, status);
        typeOfTask = TypeOfTask.EPIC;
        subtaskIds = new ArrayList<>();
    }

    public EpicTask(int id, String name, String description, Status status, TypeOfTask typeOfTask) {
        super(id, name, description, status, typeOfTask);
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