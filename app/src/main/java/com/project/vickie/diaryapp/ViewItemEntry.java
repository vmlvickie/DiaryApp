package com.project.vickie.diaryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.DatabaseHelper;
import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.EditItem;
import com.project.vickie.diaryapp.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class ViewItemEntry extends AppCompatActivity {

    TextView tv_date;
    TextView tv_title;
    TextView tv_activity;
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    int itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_activity = (TextView) findViewById(R.id.tv_activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             itemId = (int) bundle.get("CLICKED_ITEM_ID");

           // Toast.makeText(this, "itemId: " + itemId, Toast.LENGTH_LONG).show();

            Cursor c = dbHelper.getDiaryEntry(itemId);
            if(c != null){
                Item itemObj = getItem(c);
                if(itemObj != null){
                    initializeWidgets(itemObj);
                }
            }
        }
    }

    //initialize components
    private void initializeWidgets(Item item) {
        if(item.getDate() != null){
            tv_date.setText(item.getDate());
        }else{
            tv_date.setText(":");
        }

        if(item.getTitle() != null){
            tv_title.setText(item.getTitle());
        }else{
            tv_title.setText(":");
        }

        if(item.getActivity() != null){
            tv_activity.setText(item.getActivity());
        }else{
            tv_activity.setText(":");
        }

    }

    public Item getItem(Cursor c) {
        Item item= null;
        while (c.moveToNext()) {
            item = new Item(
                    c.getInt(c.getColumnIndexOrThrow("_id")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getString(c.getColumnIndexOrThrow("title")),
                    c.getString(c.getColumnIndexOrThrow("activity"))
            );
        }
        return item;
    }

    public void onClickEdit(View view){
        Intent i = new Intent(this, EditItem.class);
        i.putExtra("EDIT_ITEM_ID", itemId);
        startActivity(i);
    }
    public void onClickDelete(View view){
        long affectedRow = dbHelper.deleteJournalEntry(itemId);
        if(affectedRow > 0){
            showFeedBack("Entry was removed");
            backToDiaryActivity();
            finish();
        }
    }
    public void showFeedBack(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void backToDiaryActivity() {
        startActivity(new Intent(this, Diary.class));
        finish();
    }
}
