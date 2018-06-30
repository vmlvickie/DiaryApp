package com.project.vickie.diaryapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.AddItem;
import com.project.vickie.diaryapp.com.project.vickie.diaryapp.db.DatabaseHelper;
import com.project.vickie.diaryapp.dto.Item;
import com.project.vickie.diaryapp.dto.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Diary extends AppCompatActivity  implements RecyclerViewAdapter.RecyclerViewAdapterOnClickHandler{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewdapter;
    private RecyclerView.LayoutManager layoutManager;

    DatabaseHelper dbHelper;
    List<Item>  itemList = null;

    String TAG = "com.project.vickie.diaryapp";

    TextView empty_view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);
        Cursor c = dbHelper.getDiaryEntries();
        itemList = getItemsList(c);

        //Log.d(TAG, "ItemsCount: " + itemList.size());

        empty_view = (TextView) findViewById(R.id.empty_view);
        empty_view.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.rcViewEntries);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewdapter = new RecyclerViewAdapter(itemList, this, recyclerView, this);
        recyclerView.setAdapter(recyclerViewdapter);

        if(itemList.size() == 0){
            recyclerView.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
        }

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
                    c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("date")),
                    c.getString(c.getColumnIndex("title")),
                    c.getString(c.getColumnIndex("activity"))
            ));
        }
        return itemList;
    }

    private View.OnClickListener addDiaryItem() {
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);

        return null;
    }

    @Override
    public void onClick(int itemPosition) {
/*
        Toast.makeText(this, "itemList.get(itemPosition).getId(): " + itemList.get(itemPosition).getId(), Toast.LENGTH_LONG).show();
*/
        Intent i = new Intent(this, ViewItemEntry.class);
        i.putExtra("CLICKED_ITEM_ID",  itemList.get(itemPosition).getId());
        this.startActivity(i);
    }
}
