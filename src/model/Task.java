package model;

public abstract class Task {
    private String name;
    private String description;
    private String status;
    private static int idCount = 0;


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static int getIdCount() {
        return ++idCount;
    }

    @Override
    public String toString() {
        return "Задача: " + name + ", описание задачи: " + description + ", статус: " + status + "\n";
    }
}
