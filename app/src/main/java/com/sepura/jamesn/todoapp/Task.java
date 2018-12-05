package com.sepura.jamesn.todoapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private String name;
    private int priority;

    public static final int TASK_PRIORITY_HIGHEST    = 5;
    public static final int TASK_PRIORITY_HIGH       = 4;
    public static final int TASK_PRIORITY_NORMAL = 3;
    public static final int TASK_PRIORITY_LOW        = 2;
    public static final int TASK_PRIORITY_LOWEST     = 1;


    protected Task(Parcel in) {
        name = in.readString();
        priority = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(priority);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

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
