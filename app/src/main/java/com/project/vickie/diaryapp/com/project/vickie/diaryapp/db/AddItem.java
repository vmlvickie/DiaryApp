package com.project.vickie.diaryapp.com.project.vickie.diaryapp.db;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import java.util.Calendar;

public class AddItem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DatabaseHelper dbHelper;
    EditText etDate;
    EditText etActivity;
    EditText etTitle;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

        datePickerDialog = new DatePickerDialog(
                this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        etDate = (EditText) findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }

        });
        etActivity = (EditText) findViewById(R.id.etActivity);
        etTitle = (EditText) findViewById(R.id.etTitle);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //extrac selected date
        String dateString = view.getDayOfMonth() + "/" + view.getMonth() + "/" + view.getYear();

        etDate.setText(dateString);
    }


    public void showDatePicker(View view){
        datePickerDialog.show();
    }

    public void onAddItemClick(View view){
        String date = etDate.getText().toString();
        String title = etTitle.getText().toString();
        String activity = etActivity.getText().toString();

        if(date.replace("//s", "").equalsIgnoreCase("")){
            //Date cannot be blank
            showFeedBack("You have to select Date");
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
        long row = dbHelper.saveDiaryEntry(date, title, activity);
        if(row > 0){
            clearFields();
            backToDiaryActivity();
            showFeedBack("Entry saved.");
        }

    }

    private void clearFields() {
        etDate.setText("");
        etActivity.setText("");
    }

    public void showFeedBack(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void backToDiaryActivity(){
        startActivity(new Intent(this, Diary.class));
        finish();
    }
}
