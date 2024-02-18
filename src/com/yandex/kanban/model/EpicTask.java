    package com.yandex.kanban.model;

    import java.time.Duration;
    import java.time.LocalDateTime;
    import java.util.ArrayList;



    public class EpicTask extends Task {
        private final ArrayList<Integer> subtaskIds;

        public EpicTask() {
            this.subtaskIds = new ArrayList<>();
        }

        public EpicTask(String name, String description, Status status) {
            super(name, description, status);
            typeOfTask = TypeOfTask.EPIC;
            subtaskIds = new ArrayList<>();
        }


        public EpicTask(int id, String name, String description, Status status, TypeOfTask typeOfTask) {
            super(id, name, description, status);
            this.typeOfTask = TypeOfTask.EPIC;
            subtaskIds = new ArrayList<>();
        }


        public EpicTask(int id, String name, String description, Status status, TypeOfTask typeOfTask, Duration duration, LocalDateTime startTime) {
            super(id, name, description, status, typeOfTask, duration, startTime);
            subtaskIds = new ArrayList<>();
        }

        public ArrayList<Integer> getSubtaskIds() {
            return subtaskIds;
        }


        @Override
        public String toString() {
            String startTimeString = startTime != null ? startTime.toString() : "не определено";
            String endTimeString = endTime != null ? endTime.toString() : "не определен";
            String durationString = duration != null ? String.valueOf(duration.toMinutes()) : "не определено";
            return "Эпическая задача №" + id +
                    ", имя = " + name +
                    ", описание = " + description +
                    ", статус = " + status +
                    ", продолжительность " + durationString +
                    " мин, начало в " + startTimeString +
                    ", конец в " + endTimeString +
                    ", содержит подзадачи - " + subtaskIds;
        }

        @Override
        public LocalDateTime getEndTime() {
            return this.endTime;
        }
    }