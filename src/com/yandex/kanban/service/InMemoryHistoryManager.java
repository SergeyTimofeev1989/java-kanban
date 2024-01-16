package com.yandex.kanban.service;

import com.yandex.kanban.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> receivedTasks;
    private Node<Task> head;
    private Node<Task> tail;

    public InMemoryHistoryManager() {
        this.receivedTasks = new HashMap<>();
    }

    public void linkLast(Task task) {
        final Node<Task> newNode = new Node<Task>(tail, task, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> currentNode = head;
        while (currentNode != null) {
            tasks.add(currentNode.item);
            currentNode = currentNode.next;
        }
        return tasks;
    }

    public void removeNode(Node<Task> id) {
        Node<Task> node = receivedTasks.remove(id);
        if (node == null) {
            return;
        }
        final Node<Task> next = node.next;
        final Node<Task> prev = node.prev;
        node.item = null;

        if (head == node && tail == node) {
            head = null;
            tail = null;
        } else if (head == node && (tail != node)) {
            head = next;
            head.prev = null;
        } else if ((head != node) && tail == node) {
            tail = prev;
            tail.next = null;
        } else {
            prev.next = next;
            next.prev = prev;
        }
        node = null;
    }


    @Override
    public void add(Task task) {
        if (task != null) {
            int id = task.getId();
            remove(id);
            linkLast(task);
            receivedTasks.put(id, tail);
        }
    }

    @Override
    public void remove(int id) {
        removeNode(receivedTasks.get(id));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}

class Node<Task> {
    public Node<Task> prev;
    public Task item;
    public Node<Task> next;

    public Node(Node<Task> prev, Task item, Node<Task> next) {
        this.prev = prev;
        this.item = item;
        this.next = next;
    }
}