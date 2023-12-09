package com.yandex.kanban.model;

import java.util.ArrayList;

public class EpicTask extends Task {
    private ArrayList<Integer> subtaskIds;

    public EpicTask(String name, String description, Enum status) {
        super(name, description, status);
        subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "\n10" +
                "EpicTask{" +
                "subtaskIds=" + subtaskIds +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}