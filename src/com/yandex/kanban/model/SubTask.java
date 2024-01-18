package com.yandex.kanban.model;

public class SubTask extends Task {
    private final EpicTask epicTask;

    public SubTask(String name, String description, Status status, EpicTask epicTask) {
        super(name, description, status);
        typeOfTask = TypeOfTask.SUBTASK;
        this.epicTask = epicTask;
    }

    public SubTask(int id, String name, String description, Status status, TypeOfTask typeOfTask, EpicTask epicTask) {
        super(id, name, description, status, typeOfTask);
        this.epicTask = epicTask;
    }

    public EpicTask getEpicTask() {
        return epicTask;
    }

    @Override
    public String toString() {
        return "\n" +
                "Подзадача № " + id +
                ", принадлежит к эпической задаче №" + epicTask.getId() +
                ", имя = " + name +
                ", описание = " + description +
                ", статус = " + status;
    }
}
