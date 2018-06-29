package com.project.vickie.diaryapp.dto;

public class Item {


    private int id;
    private String date;
    private String title;
    private String Activity;

    public Item(int id, String date, String title, String activity) {
        this.id = id;
        this.date = date;
        this.title = title;
        Activity = activity;
    }

    public String getDate() {
        return date;
    }

    public Item(){

    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
