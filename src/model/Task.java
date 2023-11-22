package model;

public class Task {
    private String name;
    private String description;
    private String status;

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
        return "Задача: " + name + ", описание задачи: " + description + ", id = " + id + ", статус: " + status + "\n";
    }
}
