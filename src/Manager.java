import model.EpicTask;
import model.SimpleTask;
import model.SybTask;
import model.Task;

import java.util.*;

public class Manager {
    private static int idCount = 0;
    Scanner scanner = new Scanner(System.in);
    private Task task;
    HashMap<Integer, Task> packOfSimpleTasks = new HashMap<>();
    HashMap<Integer, Task> packOfEpicTasks = new HashMap<>();
    HashMap<Integer, Task> packOfSubTasks = new HashMap<>();


    public Manager() {
        task = new Task();
    }


    public HashMap<Integer, Task> getPackOfSimpleTasks() {
        return packOfSimpleTasks;
    }

    public HashMap<Integer, Task> getPackOfEpicTasks() {
        return packOfEpicTasks;
    }

    public HashMap<Integer, Task> getPackOfSubTasks() {
        return packOfSubTasks;
    }


    public void clearSimpleTasks() {
        packOfSimpleTasks.clear();
    }

    public void clearEpicTasks() {
        packOfEpicTasks.clear();
    }

    public void clearSubTasks() {
        packOfSubTasks.clear();
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
        System.out.println("Введите id задачи:");
        int id = scanner.nextInt();
        return packOfSubTasks.getOrDefault(id, null);
    }


    public void createSimpleTask() {
        idCount++;
        task = new Task();
        System.out.println("Введите имя задачи:");
        task.setName(scanner.nextLine());
        System.out.println("Введите описание задачи:");
        task.setDescription(scanner.nextLine());
        System.out.println("Введите статус задачи:");
        task.setStatus(scanner.nextLine());
        packOfSimpleTasks.put(idCount, task);
        System.out.println("Задача создана и помещена в список.\n");
    }

    public void createEpicTask() {
        idCount++;
        task = new Task();
        System.out.println("Введите имя задачи:");
        task.setName(scanner.nextLine());
        System.out.println("Введите описание задачи:");
        task.setDescription(scanner.nextLine());
        System.out.println("Введите статус задачи:");
        task.setStatus(scanner.nextLine());
        packOfEpicTasks.put(idCount, task);
        System.out.println("Задача создана и помещена в список.\n");
    }

    public void createSubTask() {
        idCount++;
        task = new Task();
        System.out.println("Введите имя задачи:");
        task.setName(scanner.nextLine());
        System.out.println("Введите описание задачи:");
        task.setDescription(scanner.nextLine());
        System.out.println("Введите статус задачи:");
        task.setStatus(scanner.nextLine());
        packOfSubTasks.put(idCount, task);
        System.out.println("Задача создана и помещена в список.\n");
    }


    public void updateSimpleTask() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        task = new Task();
        for(Map.Entry<Integer, Task> task : packOfSimpleTasks.entrySet()) {
            if (task.getKey() == id) {
                System.out.println("Введите имя задачи:");
                task.getValue().setName(scanner.nextLine());
                System.out.println("Введите описание задачи:");
                task.getValue().setDescription(scanner.nextLine());
                System.out.println("Введите статус задачи:");
                task.getValue().setStatus(scanner.nextLine());
                packOfSubTasks.put(id, (Task) task);
                System.out.println("Обычная задача обновлена.\n");
            }
        }
    }

    public void updateEpicTask() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        task = new Task();
        for(Map.Entry<Integer, Task> task : packOfEpicTasks.entrySet()) {
            if (task.getKey() == id) {
                System.out.println("Введите имя задачи:");
                task.getValue().setName(scanner.nextLine());
                System.out.println("Введите описание задачи:");
                task.getValue().setDescription(scanner.nextLine());
                System.out.println("Введите статус задачи:");
                task.getValue().setStatus(scanner.nextLine());
                packOfSubTasks.put(id, (Task) task);
                System.out.println("Обычная задача обновлена.\n");
            }
        }
    }

    public void updateSubTask() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        task = new Task();
        for(Map.Entry<Integer, Task> task : packOfSubTasks.entrySet()) {
            if (task.getKey() == id) {
                System.out.println("Введите имя задачи:");
                task.getValue().setName(scanner.nextLine());
                System.out.println("Введите описание задачи:");
                task.getValue().setDescription(scanner.nextLine());
                System.out.println("Введите статус задачи:");
                task.getValue().setStatus(scanner.nextLine());
                packOfSubTasks.put(id, (Task) task);
                System.out.println("Обычная задача обновлена.\n");
            }
        }
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
        System.out.println("Обычная задача удалена");
    }

    public void deleteSubTaskById() {
        System.out.println("Введите id");
        int id = scanner.nextInt();
        packOfSubTasks.remove(id);
        System.out.println("Обычная задача удалена");
    }
}
