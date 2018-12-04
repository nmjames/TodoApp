package com.sepura.jamesn.todoapp;

public class Task {
    private String name;
    private int priority;

    public static final int TASK_PRIORITY_HIGHEST    = 1;
    public static final int TASK_PRIORITY_HIGH       = 2;
    public static final int TASK_PRIORITY_MEDIUM     = 3;
    public static final int TASK_PRIORITY_LOW        = 4;
    public static final int TASK_PRIORITY_LOWEST     = 5;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}
