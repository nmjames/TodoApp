package com.sepura.jamesn.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
    @BindView(R.id.spinnerPriority) Spinner spinnerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_priority_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Apply the adapter to the spinner
        spinnerPriority.setAdapter(adapter);
        spinnerPriority.setSelection(2);

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
