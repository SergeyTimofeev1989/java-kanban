package com.yandex.kanban.model;

public class SimpleTask extends Task {
    public SimpleTask(String name, String description, Status status) {
        super(name, description, status);
        typeOfTask = TypeOfTask.TASK;
    }

    @Override
    public String toString() {
        return "\nПростая задача №" + id +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status;
    }
}
