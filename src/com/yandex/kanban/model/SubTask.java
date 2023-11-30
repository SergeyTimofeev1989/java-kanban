package com.yandex.kanban.model;

public class SubTask extends Task {
    private EpicTask epicTask;

    public SubTask(String name, String description, String status) {
        super(name, description, status);
    }

    public SubTask(String name, String description, String status, EpicTask epicTask) {
        super(name, description, status);
        this.epicTask = epicTask;
    }

    public EpicTask getEpicTask() {
        return epicTask;
    }

    public void setEpicTask(EpicTask epicTask) {
        this.epicTask = epicTask;
    }


}
