package com.sepura.jamesn.todoapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity {

    static final int ADD_TASK_CODE = 1;
    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        listView.setAdapter(new ListAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, SpeechActivity.class);
                startActivityForResult(intent, 2);
                return true;

            case R.id.settings:
                Toast.makeText(this, "Settings option", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.addButton)
    void onOkClicked() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivityForResult(intent, ADD_TASK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if( resultCode == RESULT_OK){
            String string = data.getStringExtra( TaskActivity.TASK_NAME );
            int priority = data.getIntExtra(TaskActivity.TASK_PRIORITY, 0);
            Task task = new Task(string, priority);
            ListAdapter  adapter = (ListAdapter) listView.getAdapter();
            adapter.addItem(task);

            Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        } else if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }
    }




}
