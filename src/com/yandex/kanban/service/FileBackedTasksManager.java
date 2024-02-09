package com.yandex.kanban.service;

import com.yandex.kanban.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.yandex.kanban.model.TypeOfTask.*;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private final Path path;
    private static final String FILE_NAME = "tasks.txt";
    public static final String HEADER = "id,type,name,status,description,duration,startTime,endTime,epic\n";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH:mm");

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
        stringBuilder.append(task.getDuration().toMinutes()).append(",");
        stringBuilder.append(task.getStartTime().format(dateTimeFormatter)).append(",");
        stringBuilder.append(task.getEndTime().format(dateTimeFormatter)).append(",");
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

    public void save() {
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

    public Task fromString(String value) {
        String[] arrayOfFields = value.split(",(?!\\s)");
        int id = Integer.parseInt(arrayOfFields[0]);
        TypeOfTask type = TypeOfTask.valueOf(arrayOfFields[1]);
        String name = arrayOfFields[2];
        Status status = Status.valueOf(arrayOfFields[3]);
        String description = arrayOfFields[4];
        Duration minutes = Duration.ofMinutes(Integer.parseInt(arrayOfFields[5]));
        LocalDateTime timeToStart = LocalDateTime.parse(arrayOfFields[6], dateTimeFormatter);
        LocalDateTime timeToEnd = LocalDateTime.parse(arrayOfFields[7], dateTimeFormatter);
        Task taskForReturn = null;
        if (SUBTASK == type) {
            int idOfEpic = Integer.parseInt(arrayOfFields[8]);
            taskForReturn = new SubTask(id, name, description, status, type, minutes, timeToStart, packOfEpicTasks.get(idOfEpic));
            packOfEpicTasks.get(idOfEpic).getSubtaskIds().add(id);
        }
        if (TASK == type) {
            taskForReturn = new SimpleTask(id, name, description, status, type, minutes, timeToStart, timeToEnd);
        }
        if (EPIC == type) {
            taskForReturn = new EpicTask(id, name, description, status, type, minutes, timeToStart, timeToEnd);
        }
        return taskForReturn;
    }


    public static List<Integer> historyFromString(String value) {
        String[] arrayOfId = value.split(",");
        List<Integer> listOfId = new ArrayList<>();
        for (String id : arrayOfId) {
            listOfId.add(Integer.parseInt(id));
        }
        return listOfId;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
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
                }
                if (task.getTypeOfTask() == TASK) {
                    SimpleTask simpleTask = (SimpleTask) task;
                    fileBackedTasksManager.packOfSimpleTasks.put(simpleTask.getId(), simpleTask);
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
    public int createSimpleTask(SimpleTask simpleTask) {
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
    public int createSubTask(SubTask subTask) {
        int id = super.createSubTask(subTask);
        save();
        return id;
    }

    @Override
    public int updateSimpleTask(SimpleTask simpleTask) {
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
    public int updateSubTask(SubTask subTask) {
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

    public static void main(String[] args) {
        SimpleTask simpleTask = new SimpleTask("Купить хлеб", "Бородинский с семенами; магазин работает до 19:00", Status.NEW, Duration.ofMinutes(60), LocalDateTime.now());
        EpicTask epicTask = new EpicTask("Тренировка", "16.01; подтягивания; ПШНБ; пресс", Status.NEW, Duration.ofMinutes(60), LocalDateTime.now());
        SubTask subTask = new SubTask("Пресс", "1) 8 минут на коврике; 2) подъем ног вися на турнике 4х12", Status.NEW, Duration.ofMinutes(45), LocalDateTime.now(), epicTask);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        fileBackedTasksManager.createSimpleTask(simpleTask);
        fileBackedTasksManager.createEpicTask(epicTask);
        fileBackedTasksManager.createSubTask(subTask);
        fileBackedTasksManager.getSimpleTaskById(1);
        fileBackedTasksManager.getEpicTaskById(2);
        fileBackedTasksManager.getSubTaskById(3);

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
    }
}
