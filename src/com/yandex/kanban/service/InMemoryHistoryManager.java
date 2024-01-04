package com.yandex.kanban.service;

import com.yandex.kanban.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Node<Task>> taskHistory = new LinkedList<>();
    private Map<Integer, Node<Task>> receivedTasks = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;


    public void linkLast(Task task) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<Task>(oldTail, task, null);
        tail = newNode;
        receivedTasks.put(task.getId(), newNode);
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
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

    public void removeNode(Node<Task> node) {
        if (!(node == null)) {
            final Task data = node.item;
            final Node<Task> next = node.next;
            final Node<Task> prev = node.prev;
            node.item = null;

            if (head == node && tail == node) {
                head = null;
                tail = null;
            } else if (head == node && !(tail == node)) {
                head = next;
                head.prev = null;
            } else if (!(head == node) && tail == node) {
                tail = prev;
                tail.next = null;
            } else {
                prev.next = next;
                next.prev = prev;
            }
            node = null;
        }
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            linkLast(task);
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