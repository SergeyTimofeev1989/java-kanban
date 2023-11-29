import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();
        SimpleTask simpleTask = new SimpleTask("Сходить в магазин", "Хлеб, молоко, сыр", "NEW");
        EpicTask epicTask = new EpicTask("Приготовить пиццу", "С грибами и помидорами");
        SubTask subTask = new SubTask("Сходить в магазин", "Хлеб, молоко, сыр, грибы, мука, помидоры", "NEW");

        while (true) {
            printMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                System.out.println(manager.getPackOfSimpleTasks());
            } else if (command == 2) {
                System.out.println(manager.getPackOfEpicTasks());
            } else if (command == 3) {
                System.out.println(manager.getPackOfSubTasks());
            } else if (command == 4) {
                manager.clearAllSimpleTasks();
            } else if (command == 5) {
                manager.clearAllEpicTasks();
            } else if (command == 6) {
                manager.clearAllSubTasks();
            } else if (command == 7) {
                System.out.println(manager.getSimpleTaskById(simpleTask.id));
            } else if (command == 8) {
                System.out.println(manager.getEpicTaskById(epicTask.id));
            } else if (command == 9) {
                System.out.println(manager.getSubTaskById(subTask.epicId));
            } else if (command == 10) {
                manager.createSimpleTask(simpleTask);
            } else if (command == 11) {
                manager.createEpicTask(epicTask);
            } else if (command == 12) {
                manager.createSubTask(subTask);
            } else if (command == 13) {
                manager.updateSimpleTask(simpleTask);
            } else if (command == 14) {
                manager.updateEpicTask(epicTask);
            } else if (command == 15) {
                manager.updateSubTask(subTask);
            } else if (command == 16) {
                manager.deleteSimpleTaskById(1);
            } else if (command == 17) {
                manager.deleteEpicTaskById(2);
            } else if (command == 18) {
                manager.deleteSubTaskById(3);
            } else if (command == 19) {
                System.out.println(manager.getAllSubTasksFromEpicTask());
            } else if (command == 20) {
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
        System.out.println("20. Выйти\n");
    }
}
