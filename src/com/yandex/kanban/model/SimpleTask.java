package com.yandex.kanban.model;

public class SimpleTask extends Task {
    public SimpleTask(String name, String description, Enum status) {
        super(name, description, status);
    }

    @Override
    public String toString() {
        return "\nSimpleTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
