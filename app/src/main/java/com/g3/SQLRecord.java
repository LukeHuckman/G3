package com.g3;

/*
* This is used in fragment/activity to retrieve and set individual values of a database record.
* Each class except SQLRecord represents a record of a specific table and must inherit
* from the SQLRecord class.
*/

public class SQLRecord {
    protected int id;

    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
}

class UserSettings extends SQLRecord {
    private int
        timeNotify,
        timeAlarm,
        taskNotify,
        taskAlarm,
        darkMode;

    public int getTimeNotify() {
        return timeNotify;
    }
    public void setTimeNotify(int timeNotify) {
        this.timeNotify = timeNotify;
    }

    public int getTimeAlarm() {
        return timeAlarm;
    }
    public void setTimeAlarm(int timeAlarm) {
        this.timeAlarm = timeAlarm;
    }

    public int getTaskNotify() {
        return taskNotify;
    }
    public void setTaskNotify(int taskNotify) {
        this.taskNotify = taskNotify;
    }

    public int getTaskAlarm() {
        return taskAlarm;
    }
    public void setTaskAlarm(int taskAlarm) {
        this.taskAlarm = taskAlarm;
    }

    public int getDarkMode() {
        return darkMode;
    }
    public void setDarkMode(int darkMode) {
        this.darkMode = darkMode;
    }

    @Override
    public String toString() {
        return SQLTables.AppSettings.COLUMN_ID + ":" + this.id + "," +
                SQLTables.AppSettings.COLUMN_TIME_NOTIFY + ":" + this.timeNotify + "," +
                SQLTables.AppSettings.COLUMN_TIME_ALARM + ":" + this.timeAlarm + "," +
                SQLTables.AppSettings.COLUMN_TASK_NOTIFY + ":" + this.taskNotify + "," +
                SQLTables.AppSettings.COLUMN_TASK_ALARM + ":" + this.taskAlarm + "," +
                SQLTables.AppSettings.COLUMN_DARK_MODE + ":" + this.darkMode;
    }

    public String[] toStringArray() {
        return new String[]{SQLTables.AppSettings.COLUMN_ID,
                SQLTables.AppSettings.COLUMN_TIME_NOTIFY,
                SQLTables.AppSettings.COLUMN_TIME_ALARM,
                SQLTables.AppSettings.COLUMN_TASK_NOTIFY,
                SQLTables.AppSettings.COLUMN_TASK_ALARM,
                SQLTables.AppSettings.COLUMN_DARK_MODE};
    }

}