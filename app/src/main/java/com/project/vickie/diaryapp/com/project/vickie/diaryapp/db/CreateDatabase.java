package com.project.vickie.diaryapp.com.project.vickie.diaryapp.db;
public class CreateDatabase {
    String sqlUsers = "CREATE TABLE IF NOT EXISTS \"users\" (\n" +
            "\t`_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`firstName`\tTEXT,\n" +
            "\t`lastName`\tTEXT,\n" +
            "\t`email`\tTEXT,\n" +
            "\t`username`\tTEXT,\n" +
            "\t`password`\tTEXT,\n" +
            "\t`userLevel`\tINTEGER\n" +
            ")";
    String sqlUsersInsert = "INSERT INTO `users` (_id,firstName,lastName,email,username, password,userLevel) VALUES (1,'Test','Test','test@yahoo.com', 'test@yahoo.com','test',1),\n" +
            " (3,'admin','admin','admin@yahoo.com', 'admin@yahoo.com', 'admin',0)";

    public String getSqlUsers() {
        return sqlUsers;
    }

    public void setSqlUsers(String sqlUsers) {
        this.sqlUsers = sqlUsers;
    }

    public String getSqlUsersInsert() {
        return sqlUsersInsert;
    }

    public void setSqlUsersInsert(String sqlUsersInsert) {
        this.sqlUsersInsert = sqlUsersInsert;
    }

    public String getSqlDiaryLog() {
        return sqlDiaryLog;
    }

    public void setSqlDiaryLog(String sqlDiaryLog) {
        this.sqlDiaryLog = sqlDiaryLog;
    }

    public String getSqlDiaryInsert() {
        return sqlDiaryInsert;
    }

    public void setSqlDiaryInsert(String sqlDiaryInsert) {
        this.sqlDiaryInsert = sqlDiaryInsert;
    }

    String sqlDiaryLog = "CREATE TABLE IF NOT EXISTS \"diary\" (\n" +
            "\t`_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`date`\tTEXT,\n" +
            "\t`title`\tTEXT,\n" +
            "\t`activity`\tTEXT\n" +

            ")";

    String sqlDiaryInsert = "INSERT INTO `diary` (_id,date,title, activity) VALUES (1,'28/06/2018',' Title1','Went to the market'),\n" +
            " (2,'26/06/2018','Title2' ,'Had a bad day')";
}

