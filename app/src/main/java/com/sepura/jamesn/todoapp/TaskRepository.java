package com.sepura.jamesn.todoapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private TasksDao tasksDao;


    public TaskRepository(Context context) {

        tasksDao = Room.databaseBuilder(context, AppDatabase.class, "tasksDao-name")
                .allowMainThreadQueries()
                .build()
                .tasksDao();
    }

    public List<Task> readTasks(){
        List<TaskEntry> taskEntryList = tasksDao.getAll();
        List<Task> tasks = new ArrayList<>();

        for (TaskEntry entry: taskEntryList) {
            tasks.add(toTask(entry));
        }
        return tasks;
    }


    public void addTask(Task task){
        TaskEntry taskEntry = toTaskEntry(task);
        tasksDao.insertAll(taskEntry);
    }

    public void removeTask(Task task){
        tasksDao.deleteByNameAndPriority(task.getName(), task.getPriority());
    }

    private static Task toTask(TaskEntry taskEntry){
        Task task = new Task(taskEntry.getName(), taskEntry.getPriority());
        return task;
    }

    private static TaskEntry toTaskEntry(Task task){
        TaskEntry taskEntry = new TaskEntry();

        taskEntry.setName(task.getName());
        taskEntry.setPriority(task.getPriority());

        return  taskEntry;
    }

}
