
package com.sepura.jamesn.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListAdapter extends BaseAdapter {

    private List<Task> tasks = new ArrayList<>();

    ListAdapter() {
        tasks.add(new Task( "Buy beer",Task.TASK_PRIORITY_NORMAL));
    }

    public void addItem(Task task){
        tasks.add(task);
        notifyDataSetChanged();
    }

    private void deleteItem(int position){
        tasks.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @BindView(R.id.taskNameText) TextView taskNameText;
    @BindView(R.id.taskPriority) TextView taskPriority;
    @BindView(R.id.checkBox)  CheckBox checkBox;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.task_item, parent, false);
        }
        ButterKnife.bind(this, convertView);
        Task task = tasks.get(position);
        int priority = task.getPriority();
        taskNameText.setText(task.getName());
        taskPriority.setText(Integer.toString(priority));
        checkBox.setChecked(false);
        checkBox.setOnCheckedChangeListener( (compoundButton, isChecked) -> {
            if(isChecked) {
                deleteItem(position);
            }
        });

        convertView.setBackgroundResource(getPriorityColor(priority));

        return convertView;
    }

    private int getPriorityColor(int priority){
        int color = R.color.white;
        switch(priority){
            case Task.TASK_PRIORITY_HIGHEST:
                color = R.color.blue;
                break;
            case Task.TASK_PRIORITY_HIGH:
                color = R.color.red;
                break;
            case Task.TASK_PRIORITY_NORMAL:
                color = R.color.green;
                break;
            case Task.TASK_PRIORITY_LOW:
                color = R.color.yellow;
                break;
            case Task.TASK_PRIORITY_LOWEST:
                color = R.color.cyan;
                break;
        }
        return color;
    }

}
