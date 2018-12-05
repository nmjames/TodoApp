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
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity {

    static final int ADD_TASK_CODE = 1;
    static final int EDIT_TASK_CODE = 2;
    @BindView(R.id.listView)
    ListView listView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener((parent, view, position,id) -> {

            Task task = (Task)listAdapter.getItem(position);

            Intent intent = new Intent(this, TaskActivity.class);

            intent.putExtra(TaskActivity.POSITION_REF, position);
            intent.putExtra(TaskActivity.TASK_REF, task);
            startActivityForResult(intent, EDIT_TASK_CODE);


            Toast.makeText(this, "clicked item" + position + "id" +id, Toast.LENGTH_LONG).show();
        });
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
                Toast.makeText(this, getString(R.string.option_settings), Toast.LENGTH_LONG).show();
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
            Task task = data.getParcelableExtra(TaskActivity.TASK_REF);
            int position = data.getIntExtra(TaskActivity.POSITION_REF, -1);

            if(position == -1){
                listAdapter.addItem(task);
            } else {
                listAdapter.setItem(position, task);
            }



        } else if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, R.string.button_negative, Toast.LENGTH_LONG).show();
        }
    }




}
