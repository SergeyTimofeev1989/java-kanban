package com.yandex.kanban.model;

import java.util.ArrayList;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Enum status;


    public Task(String name, String description, Enum status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public Enum getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
