package com.g3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.g3.SQLTables.AppSettings;

/*
 * SQLite database implementation for SettingsFragment.
 */

public class SettingsDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "settings.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppSettings.TABLE_NAME + "(" +
                    AppSettings.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AppSettings.COLUMN_TIME_NOTIFY + " INTEGER," +
                    AppSettings.COLUMN_TASK_NOTIFY + " INTEGER," +
                    AppSettings.COLUMN_DARK_MODE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppSettings.TABLE_NAME;

    public SettingsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public UserSettings getSettings(int i) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + AppSettings.TABLE_NAME + " where id="+i+"", null);
        cursor.moveToFirst();
        UserSettings userSettings = new UserSettings();
        userSettings.setID(cursor.getInt(0));
        userSettings.setTimeNotify(cursor.getInt(1));
        userSettings.setTaskNotify(cursor.getInt(2));
        userSettings.setDarkMode(cursor.getInt(3));
        return userSettings;
    }

    public void initSettings() { // Only called during first-time run
        ContentValues values = new ContentValues();
        values.put(AppSettings.COLUMN_ID, 1);
        values.put(AppSettings.COLUMN_TIME_NOTIFY, 0);
        values.put(AppSettings.COLUMN_TASK_NOTIFY, 0);
        values.put(AppSettings.COLUMN_DARK_MODE, 0);

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(AppSettings.TABLE_NAME, null, values);
    }

    public void updateSettings(int id, UserSettings userSettings) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppSettings.COLUMN_TIME_NOTIFY, userSettings.getTimeNotify());
        values.put(AppSettings.COLUMN_TASK_NOTIFY, userSettings.getTaskNotify());
        values.put(AppSettings.COLUMN_DARK_MODE, userSettings.getDarkMode());
        database.update(AppSettings.TABLE_NAME, values, AppSettings.COLUMN_ID + " = ? " ,
                new String[]{String.valueOf(id)});
    }
}
