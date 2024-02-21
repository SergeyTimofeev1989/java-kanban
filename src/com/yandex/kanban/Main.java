package com.yandex.kanban;

import com.yandex.kanban.model.*;
import com.yandex.kanban.service.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        InMemoryTaskManager fileBackedTasksManager1 = new InMemoryTaskManager();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        SimpleTask simpleTask = new SimpleTask("Простая задача 1", "Хлеб, молоко, сыр", Status.NEW, Duration.ofMinutes(10), LocalDateTime.of(2024,12,12,9,0));
        EpicTask epicTaskOne = new EpicTask("Эпическая задача 1", "С грибами и помидорами", Status.NEW);
        EpicTask epicTaskTwo = new EpicTask("Эпическая задача 2", "С ананасами", Status.NEW);
        SubTask subTaskOne = new SubTask("Подзадача 1", "Хлеб, молоко, сыр, грибы, мука, помидоры", Status.NEW, Duration.ofMinutes(15), LocalDateTime.now(), epicTaskOne);
        SubTask subTaskTwo = new SubTask("Подзадача 2", "Хлеб, молоко, сыр, грибы, мука, помидоры", Status.NEW, Duration.ofMinutes(1), LocalDateTime.now().plusHours(1),epicTaskOne);
        SubTask subTaskThree = new SubTask("Подзадача 3", "Хлеб, молоко, сыр, грибы, мука, помидоры", Status.NEW, Duration.ofMinutes(10), LocalDateTime.of(2025,1,1,1,1), epicTaskOne);


        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                System.out.println(fileBackedTasksManager.getPackOfSimpleTasks());
            } else if (command == 2) {
                System.out.println(fileBackedTasksManager.getPackOfEpicTasks());
            } else if (command == 3) {
                System.out.println(fileBackedTasksManager.getPackOfSubTasks());
            } else if (command == 4) {
                fileBackedTasksManager.clearAllSimpleTasks();
            } else if (command == 5) {
                fileBackedTasksManager.clearAllEpicTasks();
            } else if (command == 6) {
                fileBackedTasksManager.clearAllSubTasks();
            } else if (command == 7) {
                System.out.println(fileBackedTasksManager.getSimpleTaskById(1));
            } else if (command == 8) {
                System.out.println(fileBackedTasksManager.getEpicTaskById(1));
            } else if (command == 9) {
                System.out.println(fileBackedTasksManager.getSubTaskById(3));
            } else if (command == 10) {
                fileBackedTasksManager.createSimpleTask(simpleTask);
            } else if (command == 11) {
                fileBackedTasksManager.createEpicTask(epicTaskOne);
                fileBackedTasksManager.createEpicTask(epicTaskTwo);
            } else if (command == 12) {
                fileBackedTasksManager.createSubTask(subTaskOne);
                fileBackedTasksManager.createSubTask(subTaskTwo);
                fileBackedTasksManager.createSubTask(subTaskThree);
            } else if (command == 13) {
                fileBackedTasksManager.updateSimpleTask(simpleTask);
            } else if (command == 14) {
                fileBackedTasksManager.updateEpicTask(epicTaskOne);
            } else if (command == 15) {
                fileBackedTasksManager.updateSubTask(subTaskOne);
            } else if (command == 16) {
                fileBackedTasksManager.deleteSimpleTaskById(1);
            } else if (command == 17) {
                fileBackedTasksManager.deleteEpicTaskById(2);
            } else if (command == 18) {
                fileBackedTasksManager.deleteSubTaskById(3);
            } else if (command == 19) {
                System.out.println(fileBackedTasksManager.getEpicSubtasks(1));
            } else if (command == 20) {
                System.out.println(fileBackedTasksManager.getHistory());
            } else if (command == 21) {
                return;
            } else {
                System.out.println("Нет такой команды.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("\nЧто вы хотите сделать?");
        System.out.println("1. Получить список всех обычных задач.");
        System.out.println("2. Получить список всех эпических задач.");
        System.out.println("3. Получить список всех подзадач.");
        System.out.println("4. Удалить все обычные задачи.");
        System.out.println("5. Удалить все эпические задачи.");
        System.out.println("6. Удалить все подзадачи.");
        System.out.println("7. Получить обычную задачу по id.");
        System.out.println("8. Получить эпическую задачу по id.");
        System.out.println("9. Получить подзадачу по id.");
        System.out.println("10. Создать обычную задачу.");
        System.out.println("11. Создать эпическую задачу.");
        System.out.println("12. Создать подзадачу.");
        System.out.println("13. Обновить простую задачу по id.");
        System.out.println("14. Обновить эпическую задачу по id.");
        System.out.println("15. Обновить подзадачу по id.");
        System.out.println("16. Удалить простую задачу по id.");
        System.out.println("17. Удалить эпическую задачу по id.");
        System.out.println("18. Удалить подзадачу по id.");
        System.out.println("19. Получить список всех подзадач определённого эпика.");
        System.out.println("20. Получить историю просмотров задач.");
        System.out.println("21. Выйти\n");
    }
}
