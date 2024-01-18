package com.yandex.kanban.model;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected TypeOfTask typeOfTask;



    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id, String name, String description, Status status, TypeOfTask typeOfTask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeOfTask = typeOfTask;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TypeOfTask getTypeOfTask() {
        return typeOfTask;
    }

    public void setTypeOfTask(TypeOfTask typeOfTask) {
        this.typeOfTask = typeOfTask;
    }
}
