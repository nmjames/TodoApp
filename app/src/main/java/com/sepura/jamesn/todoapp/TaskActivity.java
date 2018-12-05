package com.sepura.jamesn.todoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskActivity extends AppCompatActivity {
    public static final String TASK_REF = "ADD_TASK_KEY";
    public static final String POSITION_REF = "POSITION_KEY";

    private int position = -1;

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
                endAction(RESULT_OK);
                return true;
            }
            return false;
        });
    }



    @OnClick(R.id.okButton)
    void onOkClicked() {
        endAction(RESULT_OK);
    }

    @OnClick(R.id.cancelButton)
    void onCancelClicked(){
        endAction(RESULT_CANCELED);
    }

    private void endAction(int result){
        Intent intent = new Intent();

        if(result == RESULT_OK) {

            String tmp = textPriority.getText().toString();
            int priority = 0;
            if (!tmp.isEmpty()){
                priority = Integer.parseInt(tmp);
            }

            Task task = new Task(textView.getText().toString(), priority);

            if (isTaskValid(task)){
                intent.putExtra(TASK_REF, task);
                intent.putExtra(POSITION_REF, position);
                setResult(result, intent);
                finish();
            }
        } else {
            setResult(result, intent);
            finish();
        }
    }



    private boolean isTaskValid(Task task){
        String string = task.getName();
        int priority = task.getPriority();

        AtomicBoolean result = new AtomicBoolean(true);

        if(string.isEmpty()){
            showInvalidDescriptionWarning();
            result.set(false);
        } else  if( (priority < Task.TASK_PRIORITY_LOWEST) || (priority > Task.TASK_PRIORITY_HIGHEST) ) {
            showInvalidPriorityWarning();
            result.set(false);
        }

        return result.get();
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
                .setPositiveButton(R.string.button_positive,(dialogInterface, i) -> {})
                .create();
        dialog.show();
    }
}
