package ng.com.tinweb.www.simone20.data;

import android.provider.BaseColumns;

/**
 * Created by kamiye on 08/09/2016.
 */
public final class DbContract {

    private DbContract() {}

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";

    static final String SQL_CREATE_REMINDER_TABLE =
            "CREATE TABLE " + ReminderSchema.TABLE_NAME + " (" +
                    ReminderSchema._ID + " INTEGER PRIMARY KEY," +
                    ReminderSchema.COLUMN_NAME_CONTACT_ID + TEXT_TYPE + COMMA_SEP +
                    ReminderSchema.COLUMN_NAME_CONTACT_NAME + TEXT_TYPE + COMMA_SEP +
                    ReminderSchema.COLUMN_NAME_CONTACT_GROUP + TEXT_TYPE + COMMA_SEP +
                    ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL + INT_TYPE + COMMA_SEP +
                    ReminderSchema.COLUMN_NAME_DATE_DUE + TEXT_TYPE + " )";

    static final String SQL_CREATE_GROUP_TABLE =
            "CREATE TABLE " + GroupSchema.TABLE_NAME + " (" +
                    GroupSchema._ID + " INTEGER PRIMARY KEY," +
                    GroupSchema.COLUMN_NAME_GROUP_ID + TEXT_TYPE + COMMA_SEP +
                    GroupSchema.COLUMN_NAME_GROUP_NAME + TEXT_TYPE + COMMA_SEP +
                    GroupSchema.COLUMN_NAME_GROUP_MEMBERS + TEXT_TYPE + COMMA_SEP +
                    GroupSchema.COLUMN_NAME_GROUP_INTERVAL + INT_TYPE + " )";



    static final String SQL_DELETE_REMINDER_TABLE =
            "DROP TABLE IF EXISTS " + ReminderSchema.TABLE_NAME;

    static final String SQL_DELETE_GROUP_TABLE =
            "DROP TABLE IF EXISTS " + GroupSchema.TABLE_NAME;


    public static class ReminderSchema implements BaseColumns {
        public static final String TABLE_NAME = "reminders";
        public static final String COLUMN_NAME_CONTACT_ID = "contact_id";
        public static final String COLUMN_NAME_CONTACT_NAME = "contact_name";
        public static final String COLUMN_NAME_CONTACT_GROUP = "contact_group_id";
        public static final String COLUMN_NAME_REMINDER_INTERVAL = "interval";
        public static final String COLUMN_NAME_DATE_DUE = "date_due";
    }

    public static class GroupSchema implements BaseColumns {
        public static final String TABLE_NAME = "groups";
        public static final String COLUMN_NAME_GROUP_ID = "group_id";
        public static final String COLUMN_NAME_GROUP_NAME = "name";
        public static final String COLUMN_NAME_GROUP_MEMBERS = "total_members";
        public static final String COLUMN_NAME_GROUP_INTERVAL = "interval";
    }



}
