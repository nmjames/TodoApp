package com.sepura.jamesn.todoapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class TaskEntry {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name="priority")
    private int priority;


    public int getPriority(){
        return priority;
    }

    public String getName() {
        return name;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public void setName(String name) {
        this.name = name;
    }
}






