package com.project.vickie.diaryapp.com.project.vickie.diaryapp.db;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.project.vickie.diaryapp.Diary;
import com.project.vickie.diaryapp.R;
import com.project.vickie.diaryapp.dto.Item;

import java.util.Calendar;

public class EditItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    int itemId;
    DatabaseHelper dbHelper;
    DatePickerDialog datePickerDialog;

    EditText etTitle;
    EditText etDate;
    EditText etActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datePickerDialog = new DatePickerDialog(
                this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        etDate = (EditText) findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        etTitle = (EditText) findViewById(R.id.etTitle);
        etActivity = (EditText) findViewById(R.id.etActivity);

        dbHelper = new DatabaseHelper(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            itemId = (int) bundle.get("EDIT_ITEM_ID");
            Cursor c = dbHelper.getDiaryEntry(itemId);

            initializeComponents(getItem(c));
        }
    }

    private void initializeComponents(Item item) {
        etDate.setText(item.getDate());
        etTitle.setText(item.getTitle());
        etActivity.setText(item.getActivity());
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

    public void onSaveItemClick(View view){
        String date = etDate.getText().toString();
        String title = etTitle.getText().toString();
        String activity = etActivity.getText().toString();

        if(date.replace("//s", "").equalsIgnoreCase("")){
            //Date cannot be blank
            showFeedBack("You have to enter Date");
            return;
        }

        if(title.replace("//s", "").equalsIgnoreCase("")){
            //Activity cannot be blank
            showFeedBack("You have to enter Title");
            return;
        }

        if(activity.replace("//s", "").equalsIgnoreCase("")){
            //Activity cannot be blank
            showFeedBack("You have to enter activity");
            return;
        }

        //else Save details
        long row = dbHelper.editJournalEntry(itemId, date, title, activity);
        if(row > 0){
            showFeedBack("Saved");
            backToDiaryActivity();
        }
    }

    public void onClickCancel(View view){
        finish();
            //backToDiaryActivity();
    }

    private void backToDiaryActivity() {
        startActivity(new Intent(this, Diary.class));
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            //extrac selected date
            String dateString = view.getDayOfMonth() + "/" + view.getMonth() + "/" + view.getYear();
            etDate.setText(dateString);
    }

    public void showFeedBack(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
