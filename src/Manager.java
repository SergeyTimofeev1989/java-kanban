import model.EpicTask;
import model.SimpleTask;
import model.SubTask;
import model.Task;

import java.util.*;

public class Manager {
    Scanner scanner = new Scanner(System.in);
    HashMap<Integer, SimpleTask> packOfSimpleTasks;
    HashMap<Integer, EpicTask> packOfEpicTasks;

    public Manager() {
        this.packOfSimpleTasks = new HashMap<>();
        this.packOfEpicTasks = new HashMap<>();
    }

    public HashMap<Integer, SimpleTask> getPackOfSimpleTasks() {
        return packOfSimpleTasks;
    }

    public HashMap<Integer, EpicTask> getPackOfEpicTasks() {
        return packOfEpicTasks;
    }

    public HashMap<Integer, SubTask> getPackOfSubTasks() {
        HashMap<Integer, SubTask> taskForReturn = new HashMap<>();
        for (Map.Entry<Integer, EpicTask> epicTaskEntry : packOfEpicTasks.entrySet()) {
            taskForReturn.putAll(epicTaskEntry.getValue().getSubTasksInEpic());
        }
        return taskForReturn;
    }


    public void clearAllSimpleTasks() {
        packOfSimpleTasks.clear();
        System.out.println("Список простых задач очищен.");
    }

    public void clearAllEpicTasks() {
        packOfEpicTasks.clear();
        System.out.println("Список эпических задач очищен.");
    }

    public void clearAllSubTasks() {
        for (Map.Entry<Integer, EpicTask> epicTaskEntry : packOfEpicTasks.entrySet()) {
            epicTaskEntry.getValue().getSubTasksInEpic().clear();
        }
        changeStatus();
        System.out.println("Список подзадач очищен.");
    }


    public Task getSimpleTaskById() {
        System.out.println("Введите id простой задачи:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Вот Ваша простая задача: ");
        return packOfSimpleTasks.getOrDefault(id, null);
    }

    public Task getEpicTaskById() {
        System.out.println("Введите id эпической задачи:");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Вот Ваша эпическая задача");
        return packOfEpicTasks.getOrDefault(id, null);
    }

    public Task getSubTaskById() {
        SubTask taskForReturn = null;
        System.out.println("Введите id подзадачи задачи:");
        int id = Integer.parseInt(scanner.nextLine());
        for (Map.Entry<Integer, EpicTask> epicTaskEntry : packOfEpicTasks.entrySet()) {
            for (Map.Entry<Integer, SubTask> subTask : epicTaskEntry.getValue().getSubTasksInEpic().entrySet()) {
                if (subTask.getKey() == id) {
                    taskForReturn = subTask.getValue();
                    break;
                }
            }
        }
        System.out.println("Вот Ваша подзадача:");
        return taskForReturn;
    }


    public void createSimpleTask() {
        int id = Task.getIdCount();
        SimpleTask simpleTask = new SimpleTask();
        System.out.println("Введите имя простой задачи:");
        simpleTask.setName(scanner.nextLine());
        System.out.println("Введите описание простой задачи:");
        simpleTask.setDescription(scanner.nextLine());
        System.out.println("Введите статус простой задачи:");
        simpleTask.setStatus(scanner.nextLine());
        packOfSimpleTasks.put(id, simpleTask);
        System.out.println("Простая задача создана и помещена в список.\n");
    }

    public void createEpicTask() {
        int id = Task.getIdCount();
        HashMap<Integer, SubTask> subTasks = new HashMap<>();
        EpicTask epicTask = new EpicTask(subTasks);
        System.out.println("Введите имя эпической задачи:");
        epicTask.setName(scanner.nextLine());
        System.out.println("Введите описание эпической задачи:");
        epicTask.setDescription(scanner.nextLine());
        epicTask.setStatus("NEW");
        packOfEpicTasks.put(id, epicTask);
        System.out.println("Эпическая задача создана и помещена в список.\n");
    }

    public void createSubTask() {
        int id = Task.getIdCount();
        SubTask subTask = new SubTask();
        System.out.println("Введите имя подзадачи:");
        subTask.setName(scanner.nextLine());
        System.out.println("Введите описание подзадачи:");
        subTask.setDescription(scanner.nextLine());
        System.out.println("Введите статус подзадачи:");
        String status = scanner.nextLine();
        if (!(status.equals("NEW") || status.equals("DONE") || status.equals("IN_PROGRESS"))) {
            System.out.println("Такого статуса не может быть. Задача не будет создана.");
            return;
        } else {
            subTask.setStatus(status);
        }
        System.out.println("Ведите id эпик задачи, куда вы хотите добавить эту подзадачу:");
        int idOfEpicTask = Integer.parseInt(scanner.nextLine());
        for (Map.Entry<Integer, EpicTask> epicTaskEntry : packOfEpicTasks.entrySet()) {
            if (epicTaskEntry.getKey() == idOfEpicTask) {
                epicTaskEntry.getValue().getSubTasksInEpic().put(id, subTask);
            }
        }
        changeStatus();
        System.out.println("Подзадача создана и помещена в список.\n");
    }


    public void updateSimpleTask() {
        System.out.println("Введите id простой задачи");
        int id = Integer.parseInt(scanner.nextLine());
        SimpleTask simpleTask = new SimpleTask();
        for (Map.Entry<Integer, SimpleTask> task : packOfSimpleTasks.entrySet()) {
            if (task.getKey() == id) {
                System.out.println("Введите новое имя простой задачи:");
                task.getValue().setName(scanner.nextLine());
                System.out.println("Введите новое описание простой задачи:");
                task.getValue().setDescription(scanner.nextLine());
                System.out.println("Введите новый статус простой задачи:");
                task.getValue().setStatus(scanner.nextLine());
                packOfSimpleTasks.put(id, simpleTask);
                System.out.println("Простая задача обновлена.\n");
            }
        }
    }

    public void updateEpicTask() {
        System.out.println("Введите id эпической задачи");
        int epicId = Integer.parseInt(scanner.nextLine());
        for (Map.Entry<Integer, EpicTask> epicTaskEntry : packOfEpicTasks.entrySet()) {
            if (epicTaskEntry.getKey() == epicId) {
                System.out.println("Введите новое имя эпической задачи:");
                epicTaskEntry.getValue().setName(scanner.nextLine());
                System.out.println("Введите новое описание эпической задачи:");
                epicTaskEntry.getValue().setDescription(scanner.nextLine());
                System.out.println("Эпическая задача обновлена.\n");
            }
        }
    }

    public void updateSubTask() {
        SubTask subTasks = new SubTask();
        System.out.println("Ведите id эпик задачи, где вы хотите обновить эту подзадачу:");
        int epicId = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите id подзадачи, которую вы хотите обновить:");
        int subId = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите новое имя подзадачи:");
        subTasks.setName(scanner.nextLine());
        System.out.println("Введите новое описание подзадачи:");
        subTasks.setDescription(scanner.nextLine());
        System.out.println("Введите новый статус подзадачи:");
        subTasks.setStatus(scanner.nextLine());
        packOfEpicTasks.get(epicId).getSubTasksInEpic().put(subId, subTasks);
        changeStatus();
    }


    public void deleteSimpleTaskById() {
        System.out.println("Введите id");
        int id = Integer.parseInt(scanner.nextLine());
        packOfSimpleTasks.remove(id);
        System.out.println("Обычная задача удалена");
    }

    public void deleteEpicTaskById() {
        System.out.println("Введите id");
        int id = Integer.parseInt(scanner.nextLine());
        packOfEpicTasks.remove(id);
        System.out.println("Эпическая задача удалена");
    }

    public void deleteSubTaskById() {
        System.out.println("Введите id епик задачи, где вы хотите удалить подзадачу");
        int epicId = Integer.parseInt(scanner.nextLine());
        System.out.println("Введите id подзадачи");
        int subId = Integer.parseInt(scanner.nextLine());
        packOfEpicTasks.get(epicId).getSubTasksInEpic().remove(subId);
        System.out.println("Подзадача удалена");
        changeStatus();
    }


    public Map<Integer, SubTask> getAllSubTasksFromEpicTask() {
        Map<Integer, SubTask> mapForReturn = null;
        System.out.println("Введите id эпика");
        int id = Integer.parseInt(scanner.nextLine());
        for (Map.Entry<Integer, EpicTask> epicTask : packOfEpicTasks.entrySet()) {
            if (epicTask.getKey() == id) {
                mapForReturn = epicTask.getValue().getSubTasksInEpic();
            }
        }
        System.out.println("Вот все подзадачи эпической задачи № " + id);
        return mapForReturn;
    }


    public void changeStatus() {
        for (EpicTask epicTask : packOfEpicTasks.values()) {
            int countOfNewStatus = 0;
            int countOfDoneStatus = 0;
            int justCount = 0;
            for (SubTask subTask : epicTask.getSubTasksInEpic().values()) {
                if (subTask.getStatus().equals("NEW")) {
                    countOfNewStatus++;
                    justCount++;
                } else if (subTask.getStatus().equals("DONE")) {
                    countOfDoneStatus++;
                    justCount++;
                } else if (subTask.getStatus().equals("IN_PROGRESS")) {
                    justCount++;
                }
            }
            if (epicTask.getSubTasksInEpic().size() == 0 || justCount != 0 && countOfNewStatus == justCount) {
                epicTask.setStatus("NEW");
            } else if (epicTask.getSubTasksInEpic().size() == countOfDoneStatus && justCount != 0) {
                epicTask.setStatus("DONE");
            } else {
                epicTask.setStatus("IN_PROGRESS");
            }
        }
    }
}
