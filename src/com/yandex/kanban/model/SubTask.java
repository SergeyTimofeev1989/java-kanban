package com.yandex.kanban.model;

public class SubTask extends Task {
    private EpicTask epicTask;

    public SubTask(String name, String description, Enum status, EpicTask epicTask) {
        super(name, description, status);
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
