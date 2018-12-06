package com.sepura.jamesn.todoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class TaskJsonRepository {
    Gson gson;
    SharedPreferences sharedPref;

    public TaskJsonRepository(Context context) {
        gson = new GsonBuilder().create();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void saveTasks(List<Task> taskList){
        String serialized = gson.toJson(taskList);
        sharedPref.edit().putString("TASK_LIST",serialized).apply();
    }

    public List<Task> readTasks(){
        String serialized = sharedPref.getString("TASK_LIST","");
        List<Task> taskList = gson.fromJson(serialized, new TypeToken<List<Task>>() {}.getType());
        return taskList;
    }
}
