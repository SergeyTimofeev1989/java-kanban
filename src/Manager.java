import model.EpicTask;
import model.SimpleTask;
import model.SubTask;
import model.Task;

import java.util.*;

public class Manager {
    Scanner scanner = new Scanner(System.in);
    private Task simpleTask;
    private EpicTask epicTask;
    private SubTask subTasks;

    HashMap<Integer, SimpleTask> packOfSimpleTasks = new HashMap<>();
    HashMap<Integer, EpicTask> packOfEpicTasks = new HashMap<>();
    HashMap<Integer, SubTask> packOfSubTasks = new HashMap<>();


    public HashMap<Integer, SimpleTask> getPackOfSimpleTasks() {
        return packOfSimpleTasks;
    }

    public HashMap<Integer, EpicTask> getPackOfEpicTasks() {
        return packOfEpicTasks;
    }

    public HashMap<Integer, SubTask> getPackOfSubTasks() {
        return packOfSubTasks;
    }


    public void clearSimpleTasks() {
        packOfSimpleTasks.clear();
    }

    public void clearEpicTasks() {
        packOfEpicTasks.clear();
        packOfSubTasks.clear();
    }

    public void clearSubTasks() {
        packOfSubTasks.clear();
        changeStatus();
    }


    public Task getSimpleTaskById() {
        System.out.println("Введите id задачи:");
        int id = scanner.nextInt();
        return packOfSimpleTasks.getOrDefault(id, null);
    }

    public Task getEpicTaskById() {
        System.out.println("Введите id задачи:");
        int id = scanner.nextInt();
        return packOfEpicTasks.getOrDefault(id, null);
    }

    public Task getSubTaskById() {
        SubTask taskForReturn = null;
        System.out.println("Введите id задачи:");
        int id = scanner.nextInt();
        for (Map.Entry<Integer, EpicTask> epicTask : packOfEpicTasks.entrySet()) {
            for (Map.Entry<Integer, Task> subTask : epicTask.getValue().getSubTasks().entrySet()) {
                if (subTask.getKey() == id) {
                    taskForReturn = (SubTask) subTask.getValue();
                }
            }
        }
        return taskForReturn;
    }


    public void createSimpleTask() {
        int id = Task.getIdCount();
        simpleTask = new SimpleTask();
        System.out.println("Введите имя задачи:");
        simpleTask.setName(scanner.nextLine());
        System.out.println("Введите описание задачи:");
        simpleTask.setDescription(scanner.nextLine());
        System.out.println("Введите статус задачи:");
        simpleTask.setStatus(scanner.nextLine());
        packOfSimpleTasks.put(id, (SimpleTask) simpleTask);
        System.out.println("Задача создана и помещена в список.\n");
        changeStatus();
    }

    public void createEpicTask() {
        int id = Task.getIdCount();
        epicTask = new EpicTask();
        System.out.println("Введите имя задачи:");
        epicTask.setName(scanner.nextLine());
        System.out.println("Введите описание задачи:");
        epicTask.setDescription(scanner.nextLine());
        epicTask.setStatus("NEW");
        packOfEpicTasks.put(id, epicTask);
        System.out.println("Задача создана и помещена в список.\n");
    }

    public void createSubTask() {
        int id = Task.getIdCount();
        subTasks = new SubTask();
        System.out.println("Введите имя задачи:");
        subTasks.setName(scanner.nextLine());
        System.out.println("Введите описание задачи:");
        subTasks.setDescription(scanner.nextLine());
        System.out.println("Введите статус задачи:");
        subTasks.setStatus(scanner.nextLine());
        System.out.println("Ведите id эпик задачи, куда вы хотите добавить эту подзадачу:");
        int idForSearch = scanner.nextInt();
        scanner.nextLine();
        for (Map.Entry<Integer, EpicTask> task : packOfEpicTasks.entrySet()) {
            if (task.getKey() == idForSearch) {
                task.getValue().getSubTasks().put(id, subTasks);
            }
        }
        packOfSubTasks.put(id, subTasks);
        System.out.println("Задача создана и помещена в список.\n");
        changeStatus();
    }


    public void updateSimpleTask() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        simpleTask = new Task();
        for (Map.Entry<Integer, SimpleTask> task : packOfSimpleTasks.entrySet()) {
            if (task.getKey() == id) {
                System.out.println("Введите имя задачи:");
                task.getValue().setName(scanner.nextLine());
                System.out.println("Введите описание задачи:");
                task.getValue().setDescription(scanner.nextLine());
                System.out.println("Введите статус задачи:");
                task.getValue().setStatus(scanner.nextLine());
                packOfSubTasks.put(id, (SubTask) task);
                System.out.println("Обычная задача обновлена.\n");
            }
        }
    }

    public void updateEpicTask() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        epicTask = new EpicTask();
        for (Map.Entry<Integer, EpicTask> task : packOfEpicTasks.entrySet()) {
            if (task.getKey() == id) {
                System.out.println("Введите имя задачи:");
                task.getValue().setName(scanner.nextLine());
                System.out.println("Введите описание задачи:");
                task.getValue().setDescription(scanner.nextLine());
                packOfSubTasks.put(id, (SubTask) task);
                System.out.println("Обычная задача обновлена.\n");
            }
        }
        changeStatus();
    }

    public void updateSubTask() {
        subTasks = new SubTask();
        System.out.println("Введите имя задачи:");
        subTasks.setName(scanner.nextLine());
        System.out.println("Введите описание задачи:");
        subTasks.setDescription(scanner.nextLine());
        System.out.println("Введите статус задачи:");
        subTasks.setStatus(scanner.nextLine());
        System.out.println("Ведите id эпик задачи, где вы хотите обновить эту подзадачу:");
        int id = scanner.nextInt();
        for (Map.Entry<Integer, EpicTask> task : packOfEpicTasks.entrySet()) {
            if (task.getKey() == id) {
                task.getValue().getSubTasks().put(id, subTasks);
            }
        }
        changeStatus();
    }


    public void deleteSimpleTaskById() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        packOfSimpleTasks.remove(id);
        System.out.println("Обычная задача удалена");
    }

    public void deleteEpicTaskById() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        packOfEpicTasks.remove(id);
        for (Map.Entry<Integer, EpicTask> t : packOfEpicTasks.entrySet()) {
            t.getValue().getSubTasks().clear();
        }
        System.out.println("Обычная задача удалена");
    }

    public void deleteSubTaskById() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        packOfSubTasks.remove(id);
        System.out.println("Обычная задача удалена");
        changeStatus();
    }

    public Map<Integer, Task> getTasksForEpicTask() {
        Map<Integer, Task> mapForReturn = null;
        System.out.println("Введите id эпика");
        int id = scanner.nextInt();
        for (Map.Entry<Integer, EpicTask> epicTask : packOfEpicTasks.entrySet()) {
            if (epicTask.getKey() == id) {
                mapForReturn = epicTask.getValue().getSubTasks();
            }
        }
        return mapForReturn;
    }


    public void changeStatus() {
        epicTask.setStatus("IN_PROGRESS");
        boolean statusNew = true;
        boolean statusDone = true;
        for (Map.Entry<Integer, EpicTask> epicTaskInLoop : packOfEpicTasks.entrySet()) {
            for (Task subTask : epicTaskInLoop.getValue().getSubTasks().values()) {
                if (!subTask.getStatus().equals("NEW")) {
                    statusNew = false;
                }
                else if (!subTask.getStatus().equals("DONE")) {
                    statusDone = false;
                }
            }
            if (statusNew || epicTaskInLoop.getValue().getSubTasks().size() == 0) {
                epicTaskInLoop.getValue().setStatus("NEW");
            }
            if (statusDone) {
                epicTaskInLoop.getValue().setStatus("DONE");
            }
        }
    }
}
