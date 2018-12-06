package com.sepura.jamesn.todoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Spinner;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskActivity extends AppCompatActivity {
    public static final String TASK_REF = "ADD_TASK_KEY";
    public static final String POSITION_REF = "POSITION_KEY";

    private int position = -1;

    static {
        System.loadLibrary("native-getPriority");
    }

    public native int getPriority();


    @BindView(R.id.editText) TextInputEditText textView;
    @BindView(R.id.editPriority) TextInputEditText textPriority;
    @BindView(R.id.spinnerPriority) Spinner spinnerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        Task task = getIntent().getParcelableExtra(TASK_REF);
        if(task != null){
            textView.setText(task.getName());
            textPriority.setText((""+ task.getPriority()));
            position = getIntent().getIntExtra(POSITION_REF,-1);
        }



        textView.setOnEditorActionListener((v,actionId,event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                endAction();
                return true;
            }
            return false;
        });
    }



    @OnClick(R.id.okButton)
    void onOkClicked() {
        endAction();
    }

    @OnClick(R.id.cancelButton)
    void onCancelClicked(){
        sendIntentCancel();
    }

    private void endAction(){

        String taskStr = textView.getText().toString();
        String taskPriorityStr = textPriority.getText().toString();

        if(isDescriptionValid(taskStr) && (isPriorityValid(taskPriorityStr))) {
            Task task = new Task(taskStr, Integer.parseInt(taskPriorityStr));
            sendIntentOk(task);
        }
    }

    private boolean isDescriptionValid(String description){
        boolean result = true;
        if(description.isEmpty()) {
            showInvalidDescriptionWarning();
            result = false;
        }
        return result;
    }

    private boolean isPriorityValid(String priorityStr) {
        boolean result = true;
        if (priorityStr.isEmpty()) {
            result = false;
        } else {
            int priority = Integer.parseInt(priorityStr);
            if ((priority < Task.TASK_PRIORITY_LOWEST) || (priority > Task.TASK_PRIORITY_HIGHEST)) {
                result = false;
            }
        }

        if (!result) {
            showInvalidPriorityWarning();
        }
        return result;
    }

    private void showInvalidDescriptionWarning(){
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_warning_title))
                .setMessage(getString(R.string.description_missing_message))
                .setCancelable(false)
                .setPositiveButton(R.string.button_positive,(dialogInterface, i) -> {})
                .create();
        dialog.show();
    }

    private void showInvalidPriorityWarning(){
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_warning_title))
                .setMessage(getString(R.string.priority_missing_message))
                .setCancelable(false)
                .setPositiveButton(R.string.button_positive,(dialogInterface, i) -> {
                    endUsingDefaultPriority();
                })
                .setNegativeButton(R.string.button_negative,(dialogInterface, i) -> {})
                .create();
        dialog.show();
    }

    private void endUsingDefaultPriority() {
        int pri = getPriority();
        Task task = new Task(textView.getText().toString(), pri);
        sendIntentOk(task);
    }

    private void sendIntentCancel(){
        setResult(RESULT_CANCELED, new Intent() );
        finish();
    }

    private void sendIntentOk(Task task) {
        Intent intent = new Intent();
        intent.putExtra(TASK_REF, task);
        intent.putExtra(POSITION_REF, getIntent().getIntExtra(POSITION_REF, -1));
        setResult(RESULT_OK, intent);
        finish();
    }

}
