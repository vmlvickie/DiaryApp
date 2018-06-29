package com.project.vickie.diaryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.AddItem;
import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.DatabaseHelper;
import com.project.vickie.diaryapp.dto.Item;
import com.project.vickie.diaryapp.dto.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Diary extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewdapter;
    private RecyclerView.LayoutManager layoutManager;

    DatabaseHelper dbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);
        Cursor c = dbHelper.getDiaryEntries();
        List<Item>  itemList = getItemsList(c);

        recyclerView = (RecyclerView) findViewById(R.id.rcViewEntries);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewdapter = new RecyclerViewAdapter(itemList, this, recyclerView);
        recyclerView.setAdapter(recyclerViewdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Entry", Snackbar.LENGTH_LONG)
                        .setAction("Action", addDiaryItem()).show();
            }
        });
    }

    private List<Item> getItemsList(Cursor c) {
        List<Item> itemList= new ArrayList<>();
        while (c.moveToNext()) {

            itemList.add(new Item(
                    c.getInt(c.getColumnIndexOrThrow("_id")),
                    c.getString(c.getColumnIndexOrThrow("date")),
                    c.getString(c.getColumnIndexOrThrow("title")),
                    c.getString(c.getColumnIndexOrThrow("activity"))
            ));
        }
        return itemList;
    }

    private View.OnClickListener addDiaryItem() {
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);

        return null;
    }

}
