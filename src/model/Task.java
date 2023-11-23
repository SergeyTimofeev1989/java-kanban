package model;

import java.util.HashMap;

public class Task {
    private String name;
    private String description;
    private String status;
    private static int idCount = 0;

    private HashMap<Integer, Task> subTasks = new HashMap<>();

    public HashMap<Integer, Task> getSubTasks() {
        return subTasks;
    }

    public static int getIdCount() {
        return ++idCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Задача: " + name + ", описание задачи: " + description + ", статус: " + status + "\n";
    }
}
