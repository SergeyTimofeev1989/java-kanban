package com.yandex.kanban.model;

import java.util.ArrayList;

public class EpicTask extends Task {
    private ArrayList<Integer> subtaskIds;

    public EpicTask(String name, String description) {
        super(name, description, "");
        subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

}