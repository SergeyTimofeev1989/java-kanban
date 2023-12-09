package com.yandex.kanban.service;

public final class Managers {
    private Managers(){};
    private static TaskManager getDefaultHistory() {
        return new InMemoryTaskManager();
    }
  }
