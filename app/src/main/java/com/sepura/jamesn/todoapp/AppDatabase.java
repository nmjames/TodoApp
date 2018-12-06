package com.sepura.jamesn.todoapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {TaskEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TasksDao tasksDao();
}
