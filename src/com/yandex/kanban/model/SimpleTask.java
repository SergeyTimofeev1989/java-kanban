package com.yandex.kanban.model;

public class SimpleTask extends Task {
    public SimpleTask(String name, String description, Enum status) {
        super(name, description, status);
    }

    @Override
    public String toString() {
        return "\nПростая задача №" + id +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status;
    }
}
