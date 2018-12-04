package com.sepura.jamesn.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class TaskActivity extends AppCompatActivity {

    public static final String TASK_NAME = "ADD_TASK_KEY";
    public static final String TASK_PRIORITY = "ADD_TASK_PRIORITY_KEY";

    @BindView(R.id.editText) TextView textView;
    @BindView(R.id.editPriority) TextView textPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.okButton)
    void onOkClicked() {
        Intent intent = new Intent();
        String string = textView.getText().toString();
        int priority = Integer.parseInt(textPriority.getText().toString());

        intent.putExtra(TASK_NAME, string);
        intent.putExtra(TASK_PRIORITY,priority);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.cancelButton)
    void onCancelClicked(){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

}
