import java.util.*;

public class Manager {
    protected final HashMap<Integer, SimpleTask> packOfSimpleTasks = new HashMap<>();
    protected final HashMap<Integer, EpicTask> packOfEpicTasks = new HashMap<>();
    protected final HashMap<Integer, SubTask> packOfSubtasks = new HashMap<>();
    protected int NextId = 1;


    public ArrayList<SimpleTask> getPackOfSimpleTasks() {
        return new ArrayList<>(packOfSimpleTasks.values());
    }

    public ArrayList<EpicTask> getPackOfEpicTasks() {
        return new ArrayList<>(packOfEpicTasks.values());
    }

    public ArrayList<SubTask> getPackOfSubTasks() {
        return new ArrayList<>(packOfSubtasks.values());
    }


    public void clearAllSimpleTasks() {
        packOfSimpleTasks.clear();
    }

    public void clearAllEpicTasks() {
        packOfEpicTasks.clear();
    }

    public void clearAllSubTasks() {
        packOfEpicTasks.clear();
        packOfSubtasks.clear();
    }


    public SimpleTask getSimpleTaskById(int id) {
        return packOfSimpleTasks.get(id);
    }

    public EpicTask getEpicTaskById(int id) {
        return packOfEpicTasks.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return packOfSubtasks.get(id);
    }


    public int createSimpleTask(SimpleTask task) {
        task.id = NextId++;
        packOfSimpleTasks.put(task.id, task);
        return task.id;
    }

    public int createEpicTask(EpicTask task) {
        task.id = NextId++;
        packOfEpicTasks.put(task.id, task);
        return task.id;
    }

    public int createSubTask(SubTask task) {
        task.id = NextId++;
        packOfSubtasks.put(task.id, task);
        return task.id;
    }


    public int updateSimpleTask(SimpleTask task) {
        packOfSimpleTasks.put(task.id, task);
        return task.id;
    }

    public int updateEpicTask(EpicTask task) {
        packOfEpicTasks.put(task.id, task);
        return task.id;
    }

    public int updateSubTask(SubTask task) {
        packOfSubtasks.put(task.id, task);
        return task.id;
    }


    public void deleteSimpleTaskById(int id) {
        packOfSimpleTasks.remove(id);
    }

    public void deleteEpicTaskById(int id) {
        packOfEpicTasks.remove(id);
    }

    public void deleteSubTaskById(int id) {
        packOfSubtasks.remove(id);
    }


    public ArrayList<Integer> getAllSubTasksFromEpicTask() {
        ArrayList<Integer> listForReturn = new ArrayList<>();
        for (EpicTask epicTaskEntry : packOfEpicTasks.values()) {
            listForReturn.addAll(epicTaskEntry.subtaskIds);
        };
        return listForReturn;
    }


    public void changeStatus() {

    }
}
