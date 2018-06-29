package com.project.vickie.diaryapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.DatabaseHelper;
import com.project.vickie.diaryapp.dto.Item;

import java.util.ArrayList;
import java.util.List;

public class ViewItemEntry extends AppCompatActivity {

    TextView tv_date;
    TextView tv_title;
    TextView tv_activity;
    DatabaseHelper dbHelper = null;
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
            int itemId = (int) bundle.get("CLICKED_ITEM_ID");
            //fetch cursor, Generate Item object and Initialize components
            Cursor c = dbHelper.getDiaryEntry(itemId);
            if(c != null){
                Item itemObj = getItem(c);
                initializeWidgets(itemObj);
            }


        }


    }

    //initialize components
    private void initializeWidgets(Item item) {
        tv_date.setText(item.getDate());
        tv_title.setText(item.getTitle());
        tv_activity.setText(item.getActivity());
    }

    private Item getItem(Cursor c) {
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

}
