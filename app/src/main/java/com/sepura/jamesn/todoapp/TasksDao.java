package com.sepura.jamesn.todoapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface TasksDao {
    @Query("SELECT * FROM taskentry")
    List<TaskEntry> getAll();

    @Insert
    void insertAll(TaskEntry... taskentry);

    @Query("DELETE FROM taskentry WHERE name = :name AND priority = :priority")
    void deleteByNameAndPriority(String name, int priority);

}
