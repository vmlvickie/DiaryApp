package com.project.vickie.diaryapp.com.project.vickie.diaryapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private String TABLE_USERS = "users";
    private String ID = "_id";
    private String FIRST_NAME = "firstName";
    private String LAST_NAME = "lastName";
    private String USERNAME = "username";
    private String EMAIL = "email";
    private String PASSWORD = "password";
    private String TABLE_DIARY = "diary";
    private  CreateDatabase dbCreator;

    public DatabaseHelper(Context context) {
        super(context, "diary.db", null, DATABASE_VERSION);
        dbCreator = new CreateDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabaseTables(db);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);

        onCreate(db);
    }

    private void createDatabaseTables(SQLiteDatabase db) {
        //create users table
        String sqlUsers = dbCreator.sqlUsers;
        db.execSQL(sqlUsers);
        String sqlUsersInsert = dbCreator.getSqlUsersInsert();
        db.execSQL(sqlUsersInsert);

        //create diary entries table
        String sqlDiaryLog = dbCreator.getSqlDiaryLog();
        db.execSQL(sqlDiaryLog);
        String sqldiaryInsert = dbCreator.getSqlDiaryInsert();
        db.execSQL(sqldiaryInsert);
    }

    public long registerUser(String firstNname, String lastName, String emailAddr, String userName, String confirmPass) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues userData = new ContentValues();
        userData.put(FIRST_NAME, firstNname);
        userData.put(LAST_NAME, lastName);
        userData.put(EMAIL, emailAddr);
        userData.put(USERNAME, userName);
        userData.put(PASSWORD, confirmPass);
        userData.put("userLevel", 1);
        return database.insert(TABLE_USERS, null, userData);
    }

    public Cursor validateUser(String email, String password) {
        String query = "SELECT " + PASSWORD + ", userLevel FROM " + TABLE_USERS + " WHERE "
                + EMAIL + " = '" + email + "' AND " + PASSWORD + " = '" + password + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor rCursor = db.rawQuery(query, null);
        return rCursor;
    }

    public boolean userExists(String userName) {
        String sql = "SELECT " + ID + " FROM " + TABLE_USERS + " WHERE " + USERNAME + " = '" + userName + "'";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean emailUserExists(String email) {
        String sql = "SELECT " + ID + " FROM " + TABLE_USERS + " WHERE " + EMAIL + " = '" + email + "'";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        return cursor.getCount() > 0;
    }

    //get diary logs
    public Cursor getDiaryEntries() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT _id, date, title, activity FROM " + TABLE_DIARY + " ORDER BY date ASC";
        return db.rawQuery(sql, null);
    }
    public Cursor getDiaryEntry(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT _id, date, title, activity FROM " + TABLE_DIARY +" WHERE _id = '" +id + "'";
        return db.rawQuery(sql, null);



    }
    public long editProfile(String firstNname, String lastName, String emailAddr, String userName,  String newPass) {
        ContentValues values = new ContentValues();
        values.put("firstName", firstNname);
        values.put("lastName", lastName);
        values.put("username", userName);
        values.put("email", emailAddr);
        values.put("password", newPass);
        SQLiteDatabase db = getWritableDatabase();
        return db.update("users", values, "email = " + emailAddr, null);
    }
    public Cursor getUser(String userName) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT firstName, lastName, phone, email FROM users WHERE" +
                "  username = '" + userName + "'";
        return db.rawQuery(sql, null);
    }

    public long saveDiaryEntry(String date, String title, String activity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("date", date);
        values.put("title", title);
        values.put("activity", activity);
        return db.insert(TABLE_DIARY, null, values);
    }

    public long editJournalEntry(int id, String date, String title, String activity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("date", date);
        values.put("title", title);
        values.put("activity", activity);
        return db.update(TABLE_DIARY, values, "_id = " + id, null);
    }

    public long deleteJournalEntry(int itemId){
        SQLiteDatabase db = getWritableDatabase();
            return db.delete(TABLE_DIARY, "_id = " + itemId, null);
    }

}
