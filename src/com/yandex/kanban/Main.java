package com.yandex.kanban;

import com.yandex.kanban.model.EpicTask;
import com.yandex.kanban.model.SimpleTask;
import com.yandex.kanban.model.Status;
import com.yandex.kanban.model.SubTask;
import com.yandex.kanban.service.HistoryManager;
import com.yandex.kanban.service.InMemoryHistoryManager;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        SimpleTask simpleTask = new SimpleTask("Сходить в магазин", "Хлеб, молоко, сыр", Status.NEW);
        EpicTask epicTask = new EpicTask("Приготовить пиццу", "С грибами и помидорами", Status.NEW);
        SubTask subTask = new SubTask("Сходить в магазин", "Хлеб, молоко, сыр, грибы, мука, помидоры", Status.NEW,epicTask);
        SubTask subTaskTwo = new SubTask("тест", "тест", Status.NEW);
        SubTask subTaskThree = new SubTask("Сходить в кино", "Билеты", Status.NEW);

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                System.out.println(inMemoryHistoryManager.getPackOfSimpleTasks());
            } else if (command == 2) {
                System.out.println(inMemoryHistoryManager.getPackOfEpicTasks());
            } else if (command == 3) {
                System.out.println(inMemoryHistoryManager.getPackOfSubTasks());
            } else if (command == 4) {
                inMemoryHistoryManager.clearAllSimpleTasks();
            } else if (command == 5) {
                inMemoryHistoryManager.clearAllEpicTasks();
            } else if (command == 6) {
                inMemoryHistoryManager.clearAllSubTasks();
            } else if (command == 7) {
                System.out.println(inMemoryHistoryManager.getSimpleTaskById(1));
            } else if (command == 8) {
                System.out.println(inMemoryHistoryManager.getEpicTaskById(2));
            } else if (command == 9) {
                System.out.println(inMemoryHistoryManager.getSubTaskById(3));
            } else if (command == 10) {
                inMemoryHistoryManager.createSimpleTask(simpleTask);
            } else if (command == 11) {
                inMemoryHistoryManager.createEpicTask(epicTask);
            } else if (command == 12) {
                inMemoryHistoryManager.createSubTask(subTask);
            } else if (command == 13) {
                inMemoryHistoryManager.updateSimpleTask(simpleTask);
            } else if (command == 14) {
                inMemoryHistoryManager.updateEpicTask(epicTask);
            } else if (command == 15) {
                inMemoryHistoryManager.updateSubTask(subTask);
            } else if (command == 16) {
                inMemoryHistoryManager.deleteSimpleTaskById(1);
            } else if (command == 17) {
                inMemoryHistoryManager.deleteEpicTaskById(2);
            } else if (command == 18) {
                inMemoryHistoryManager.deleteSubTaskById(3);
            } else if (command == 19) {
                System.out.println(inMemoryHistoryManager.getEpicSubtasks(1));
            } else if (command == 20) {
                inMemoryHistoryManager.getHistory();
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
