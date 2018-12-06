
package com.sepura.jamesn.todoapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Task> tasks;
    private TaskItemClickListener taskItemClickListener;
    private TaskRepository taskRepository;

    public ListAdapter(Context context, TaskItemClickListener taskItemClickListener){

        this.taskItemClickListener = taskItemClickListener;

        taskRepository = new TaskRepository(context);
        tasks = taskRepository.readTasks();

        if(tasks == null){
            tasks = new ArrayList<>();
        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.task_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Context context = viewHolder.itemView.getContext();

        Task task = tasks.get(position);
        int priority = task.getPriority();

        viewHolder.taskNameText.setText(task.getName());
        viewHolder.taskPriority.setText(Integer.toString(priority));
        viewHolder.checkBox.setChecked(false);
        viewHolder.checkBox.setOnCheckedChangeListener( (compoundButton, isChecked) -> {
            if(isChecked) {
                deleteItem(position);
            }
        });

        viewHolder.itemView.setBackgroundResource(getPriorityColor(priority));

        viewHolder.itemView.setOnClickListener((v)->{
            taskItemClickListener.onTaskClicked(task,position );

        });
    }

    public void addItem(Task task){
        tasks.add(task);
        dataSetChanged();
    }

    public void setItem(int position, Task task){
        if(position < tasks.size()){
            tasks.set(position, task);
            dataSetChanged();
        }
    }

    private void deleteItem(int position){
        tasks.remove(position);
        dataSetChanged();
    }

    private void dataSetChanged(){
        taskRepository.saveTasks(tasks);
        notifyDataSetChanged();
    }



    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return tasks.size();
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.taskNameText) TextView taskNameText;
        @BindView(R.id.taskPriority) TextView taskPriority;
        @BindView(R.id.checkBox)  CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
