package com.yandex.kanban.model;

public class SubTask extends Task {
    private final EpicTask epicTask;

    public SubTask(String name, String description, Status status, EpicTask epicTask) {
        super(name, description, status);
        typeOfTask = TypeOfTask.SUBTASK;
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
