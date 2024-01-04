package com.yandex.kanban;

import com.yandex.kanban.model.EpicTask;
import com.yandex.kanban.model.SimpleTask;
import com.yandex.kanban.model.Status;
import com.yandex.kanban.model.SubTask;
import com.yandex.kanban.service.*;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        SimpleTask simpleTask = new SimpleTask("Простая задача 1", "Хлеб, молоко, сыр", Status.NEW);
        EpicTask epicTaskOne = new EpicTask("Эпическая задача 1", "С грибами и помидорами", Status.NEW);
        EpicTask epicTaskTwo = new EpicTask("Эпическая задача 2", "С грибами и помидорами", Status.NEW);
        SubTask subTaskOne = new SubTask("Подзадача 1", "Хлеб, молоко, сыр, грибы, мука, помидоры", Status.NEW, epicTaskOne);
        SubTask subTaskTwo = new SubTask("Подзадача 2", "Хлеб, молоко, сыр, грибы, мука, помидоры", Status.NEW, epicTaskOne);
        SubTask subTaskThree = new SubTask("Подзадача 3", "Хлеб, молоко, сыр, грибы, мука, помидоры", Status.NEW, epicTaskOne);


        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                System.out.println(inMemoryTaskManager.getPackOfSimpleTasks());
            } else if (command == 2) {
                System.out.println(inMemoryTaskManager.getPackOfEpicTasks());
            } else if (command == 3) {
                System.out.println(inMemoryTaskManager.getPackOfSubTasks());
            } else if (command == 4) {
                inMemoryTaskManager.clearAllSimpleTasks();
            } else if (command == 5) {
                inMemoryTaskManager.clearAllEpicTasks();
            } else if (command == 6) {
                inMemoryTaskManager.clearAllSubTasks();
            } else if (command == 7) {
                System.out.println(inMemoryTaskManager.getSimpleTaskById(1));
            } else if (command == 8) {
                System.out.println(inMemoryTaskManager.getEpicTaskById(1));
            } else if (command == 9) {
                System.out.println(inMemoryTaskManager.getSubTaskById(3));
            } else if (command == 10) {
                inMemoryTaskManager.createSimpleTask(simpleTask);
            } else if (command == 11) {
                inMemoryTaskManager.createEpicTask(epicTaskOne);
                inMemoryTaskManager.createEpicTask(epicTaskTwo);
            } else if (command == 12) {
                inMemoryTaskManager.createSubTask(subTaskOne);
                inMemoryTaskManager.createSubTask(subTaskTwo);
                inMemoryTaskManager.createSubTask(subTaskThree);
            } else if (command == 13) {
                inMemoryTaskManager.updateSimpleTask(simpleTask);
            } else if (command == 14) {
                inMemoryTaskManager.updateEpicTask(epicTaskOne);
            } else if (command == 15) {
                inMemoryTaskManager.updateSubTask(subTaskOne);
            } else if (command == 16) {
                inMemoryTaskManager.deleteSimpleTaskById(1);
            } else if (command == 17) {
                inMemoryTaskManager.deleteEpicTaskById(2);
            } else if (command == 18) {
                inMemoryTaskManager.deleteSubTaskById(3);
            } else if (command == 19) {
                System.out.println(inMemoryTaskManager.getEpicSubtasks(1));
            } else if (command == 20) {
                System.out.println(inMemoryTaskManager.getHistoryManager());
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
