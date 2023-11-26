package model;

import java.util.HashMap;

public class EpicTask extends Task {
    private HashMap<Integer, SubTask> subTasksInEpic;

    public HashMap<Integer, SubTask> getSubTasksInEpic() {
        return subTasksInEpic;
    }

    public EpicTask(HashMap<Integer, SubTask> subTasksInEpic) {
        this.subTasksInEpic = subTasksInEpic;
    }
}