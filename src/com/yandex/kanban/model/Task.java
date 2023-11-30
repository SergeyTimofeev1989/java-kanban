package com.yandex.kanban.model;

import java.util.ArrayList;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected String status;
    protected ArrayList<Integer> tasks;

    public Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
