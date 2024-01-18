package com.yandex.kanban.model;

public class SimpleTask extends Task {
    public SimpleTask(String name, String description, Status status) {
        super(name, description, status);
        typeOfTask = TypeOfTask.TASK;
    }

    public SimpleTask(int id, String name, String description, Status status, TypeOfTask typeOfTask) {
        super(id, name, description, status, typeOfTask);
    }

    @Override
    public String toString() {
        return "\nПростая задача №" + id +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status;
    }
}
