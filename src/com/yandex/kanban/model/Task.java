package com.yandex.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected TypeOfTask typeOfTask;
    protected Duration duration;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;


    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = Duration.ofMinutes(duration.toMinutes());
        this.startTime = startTime;
        this.endTime = startTime.plus(duration);
    }

    public Task(int id, String name, String description, Status status, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.typeOfTask = typeOfTask;
        this.duration = Duration.ofMinutes(duration.toMinutes());
        this.startTime = startTime;
        this.endTime = startTime.plus(duration);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TypeOfTask getTypeOfTask() {
        return typeOfTask;
    }


    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }


    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
