package com.yandex.kanban.model;

import java.util.ArrayList;

public class EpicTask extends Task {
    private ArrayList<Integer> subtaskIds;
    private ArrayList<SubTask> subTasks;

    public EpicTask(String name, String description) {
        super(name, description, "");
        subtaskIds = new ArrayList<>();
        subTasks = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void setSubtaskIds(ArrayList<Integer> subtaskIds) {
        this.subtaskIds = subtaskIds;
    }
}