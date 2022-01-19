package com.g3;

import android.provider.BaseColumns;

public class SQLTables {
    public SQLTables() {}

    public abstract class AppSettings implements BaseColumns {
        public static final String TABLE_NAME = "app_settings";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TIME_NOTIFY = "time_notify";
        public static final String COLUMN_TIME_ALARM = "time_alarm";
        public static final String COLUMN_TASK_NOTIFY = "task_notify";
        public static final String COLUMN_TASK_ALARM = "task_alarm";
        public static final String COLUMN_DARK_MODE = "dark_mode";
    }
}
