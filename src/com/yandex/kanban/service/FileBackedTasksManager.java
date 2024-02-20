package com.yandex.kanban.service;

import com.yandex.kanban.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yandex.kanban.model.TypeOfTask.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private final Path path;
    private static final String FILE_NAME = "tasks.txt";
    private static final String HEADER = "id,type,name,status,description,duration,startTime,endTime,epic\n";

    public FileBackedTasksManager() {
        path = Paths.get("src/com/yandex/kanban/files/", FILE_NAME);
    }

    public String toString(Task task) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(task.getId()).append(",");
        stringBuilder.append(task.getTypeOfTask()).append(",");
        stringBuilder.append(task.getName()).append(",");
        stringBuilder.append(task.getStatus()).append(",");
        stringBuilder.append(task.getDescription()).append(",");
        if (task.getStartTime() != null) {
            stringBuilder.append(task.getDuration().toMinutes()).append(",");
            stringBuilder.append(task.getStartTime()).append(",");
            stringBuilder.append(task.getEndTime()).append(",");
        } else {
            stringBuilder.append("null,null.null,");
        }
        if (task.getTypeOfTask() == SUBTASK) {
            stringBuilder.append(((SubTask) task).getEpicTask().getId());
        }
        return stringBuilder.append("\n").toString();
    }

    public static String historyToString(HistoryManager manager) {
        StringBuilder idOfTasks = new StringBuilder();
        for (Task task : manager.getHistory()) {
            idOfTasks.append(task.getId()).append(",");
        }
        if (idOfTasks.toString().length() != 0) {
            idOfTasks.deleteCharAt(idOfTasks.length() - 1);
        }
        return idOfTasks.toString();
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile(), StandardCharsets.UTF_8), 512)) {
            bufferedWriter.write(HEADER);
            for (Map.Entry<Integer, SimpleTask> entry : packOfSimpleTasks.entrySet()) {
                bufferedWriter.write(toString(entry.getValue()));
            }
            for (Map.Entry<Integer, EpicTask> entry : packOfEpicTasks.entrySet()) {
                bufferedWriter.write(toString(entry.getValue()));
            }
            for (Map.Entry<Integer, SubTask> entry : packOfSubtasks.entrySet()) {
                bufferedWriter.write(toString(entry.getValue()));
            }
            bufferedWriter.write("\n" + historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при сохранении задачи в файл", e);
        }
    }

    private Task fromString(String value) {
        String[] arrayOfFields = value.split(",");
        int id = Integer.parseInt(arrayOfFields[0]);
        TypeOfTask type = TypeOfTask.valueOf(arrayOfFields[1]);
        String name = arrayOfFields[2];
        Status status = Status.valueOf(arrayOfFields[3]);
        String description = arrayOfFields[4];
        Task taskForReturn = null;
        if (!arrayOfFields[5].equals("null")) {
            Duration duration = Duration.ofMinutes(Integer.parseInt(arrayOfFields[5]));
            LocalDateTime startTime = LocalDateTime.parse(arrayOfFields[6]);
            LocalDateTime endTime = LocalDateTime.parse(arrayOfFields[7]);
            if (SUBTASK == type) {
                int idOfEpic = Integer.parseInt(arrayOfFields[8]);
                taskForReturn = new SubTask(id, name, description, status, type, duration, startTime, packOfEpicTasks.get(idOfEpic));
                packOfEpicTasks.get(idOfEpic).getSubtaskIds().add(id);
            }
            if (TASK == type) {
                taskForReturn = new SimpleTask(id, name, description, status, type, duration, startTime);
            }
            if (EPIC == type) {
                taskForReturn = new EpicTask(id, name, description, status, type, duration, startTime);
                taskForReturn.setEndTime(endTime);
            }
        } else {
            if (SUBTASK == type) {
                int idOfEpic = Integer.parseInt(arrayOfFields[8]);
                taskForReturn = new SubTask(id, name, description, status, type, packOfEpicTasks.get(idOfEpic));
                packOfEpicTasks.get(idOfEpic).getSubtaskIds().add(id);
            }
            if (TASK == type) {
                taskForReturn = new SimpleTask(id, name, description, status, type);
            }
            if (EPIC == type) {
                taskForReturn = new EpicTask(id, name, description, status, type);
            }
        }
        return taskForReturn;
    }


    private static List<Integer> historyFromString(String value) {
        List<Integer> listOfId = new ArrayList<>();
        if (value != null) {
            String[] arrayOfId = value.split(",");
            for (String id : arrayOfId) {
                listOfId.add(Integer.parseInt(id));
            }
        }
        return listOfId;
    }

    private static FileBackedTasksManager loadFromFile(File file) {
        boolean isFirstString = true;
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while (bufferedReader.ready()) {
                if (isFirstString) {
                    bufferedReader.readLine();
                    isFirstString = false;
                }
                String line = bufferedReader.readLine();
                if (line.isEmpty()) {
                    Task taskForHistory = null;
                    line = bufferedReader.readLine();
                    List<Integer> listOfIds = historyFromString(line);
                    Map<Integer, Task> allTasks = new HashMap<>();
                    allTasks.putAll(fileBackedTasksManager.packOfEpicTasks);
                    allTasks.putAll(fileBackedTasksManager.packOfSubtasks);
                    allTasks.putAll(fileBackedTasksManager.packOfSimpleTasks);
                    for (Integer id : listOfIds) {
                        taskForHistory = allTasks.get(id);
                        fileBackedTasksManager.historyManager.add(taskForHistory);
                    }
                    break;
                }
                Task task = fileBackedTasksManager.fromString(line);
                if (task.getTypeOfTask() == EPIC) {
                    EpicTask epicTask = (EpicTask) task;
                    fileBackedTasksManager.packOfEpicTasks.put(epicTask.getId(), epicTask);
                }
                if (task.getTypeOfTask() == SUBTASK) {
                    SubTask subTask = (SubTask) task;
                    fileBackedTasksManager.packOfSubtasks.put(subTask.getId(), subTask);
                    fileBackedTasksManager.getPrioritizedTasks();
                }
                if (task.getTypeOfTask() == TASK) {
                    SimpleTask simpleTask = (SimpleTask) task;
                    fileBackedTasksManager.packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
                    fileBackedTasksManager.getPrioritizedTasks();
                }

            }
        } catch (IOException e) {
            throw new RuntimeException("Не получилось загрузить информацию из файла", e);
        }
        return fileBackedTasksManager;
    }


    @Override
    public void clearAllSimpleTasks() {
        super.clearAllSimpleTasks();
        save();
    }

    @Override
    public void clearAllEpicTasks() {
        super.clearAllEpicTasks();
        save();
    }

    @Override
    public void clearAllSubTasks() {
        super.clearAllEpicTasks();
        save();
    }

    @Override
    public SimpleTask getSimpleTaskById(int id) {
        SimpleTask task = super.getSimpleTaskById(id);
        save();
        return task;
    }

    @Override
    public EpicTask getEpicTaskById(int id) {
        EpicTask task = super.getEpicTaskById(id);
        save();
        return task;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask task = super.getSubTaskById(id);
        save();
        return task;
    }

    @Override
    public int createSimpleTask(SimpleTask simpleTask) throws Exception {
        int id = super.createSimpleTask(simpleTask);
        save();
        return id;
    }

    @Override
    public int createEpicTask(EpicTask epicTask) {
        int id = super.createEpicTask(epicTask);
        save();
        return id;
    }

    @Override
    public int createSubTask(SubTask subTask) throws Exception {
        int id = super.createSubTask(subTask);
        save();
        return id;
    }

    @Override
    public int updateSimpleTask(SimpleTask simpleTask) throws Exception {
        int id = super.updateSimpleTask(simpleTask);
        save();
        return id;
    }

    @Override
    public int updateEpicTask(EpicTask epicTack) {
        int id = super.updateEpicTask(epicTack);
        save();
        return id;
    }

    @Override
    public int updateSubTask(SubTask subTask) throws Exception {
        int id = super.updateSubTask(subTask);
        save();
        return id;
    }

    @Override
    public void deleteSimpleTaskById(int idOfSimpleTask) {
        super.deleteSimpleTaskById(idOfSimpleTask);
        save();
    }

    @Override
    public void deleteEpicTaskById(int idOfEpicTask) {
        super.deleteEpicTaskById(idOfEpicTask);
        save();
    }

    @Override
    public void deleteSubTaskById(int idOfSubtask) {
        super.deleteSubTaskById(idOfSubtask);
        save();
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(int epicId) {
        return super.getEpicSubtasks(epicId);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    public static void main(String[] args) throws Exception {
        SimpleTask simpleTask = new SimpleTask("Простая задача", "описание простой задачи", Status.NEW, Duration.ofMinutes(5), LocalDateTime.of(2024, 2, 13, 12, 0));
        EpicTask epicTask = new EpicTask("Эпическая задача", "описание эпика", Status.NEW);
        SubTask subTask = new SubTask("Подзадача эпика 3", "описание подзадачи", Status.NEW, Duration.ofMinutes(5), LocalDateTime.of(2024, 2, 13, 10, 0), epicTask);
        SubTask subTaskTwo = new SubTask("Подзадача эпика 4", "ПШНБ 40кг 4х8", Status.NEW, Duration.ofMinutes(5), LocalDateTime.of(2024, 2, 13, 11, 0), epicTask);
        SubTask subTaskThree = new SubTask("Подзадача эпика 5", "Задача не должна быть создана из за пересечения", Status.NEW, Duration.ofMinutes(5), LocalDateTime.of(2024, 2, 13, 11, 0), epicTask);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        try {
            fileBackedTasksManager.createSimpleTask(simpleTask);
            fileBackedTasksManager.createEpicTask(epicTask);
            fileBackedTasksManager.createSubTask(subTask);
            fileBackedTasksManager.createSubTask(subTaskTwo);
            fileBackedTasksManager.createSubTask(subTaskThree);
        } catch (Exception e) {
            System.out.println("Ошибка при создании задачи: " + e.getMessage());
        }

        fileBackedTasksManager.getSimpleTaskById(1);
        fileBackedTasksManager.getEpicTaskById(2);
        fileBackedTasksManager.getSubTaskById(3);
        fileBackedTasksManager.getSubTaskById(3);
        fileBackedTasksManager.getSimpleTaskById(1);

        FileBackedTasksManager newFileBackedTasksManager = loadFromFile(fileBackedTasksManager.path.toFile());
        Map<Integer, Task> allMaps = new HashMap<>();
        allMaps.putAll(newFileBackedTasksManager.packOfEpicTasks);
        allMaps.putAll(newFileBackedTasksManager.packOfSubtasks);
        allMaps.putAll(newFileBackedTasksManager.packOfSimpleTasks);

        for (Task entry : allMaps.values()) {
            System.out.println(entry);
        }

        for (Task task : newFileBackedTasksManager.getHistory()) {
            System.out.print(task.getId() + " ");
        }

        System.out.println();


        for (Task prioritizedTask : fileBackedTasksManager.getPrioritizedTasks()) {
            System.out.println(prioritizedTask.getName() + " " + prioritizedTask.getStartTime());
        }
    }
}
