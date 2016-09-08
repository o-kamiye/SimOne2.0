package ng.com.tinweb.www.simone20.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by kamiye on 08/09/2016.
 */
public final class ReminderContract {

    private ReminderContract() {}

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ReminderSchema.TABLE_NAME + " (" +
                    ReminderSchema._ID + " INTEGER PRIMARY KEY," +
                    ReminderSchema.COLUMN_NAME_CONTACT_ID + TEXT_TYPE + COMMA_SEP +
                    ReminderSchema.COLUMN_NAME_CONTACT_NAME + TEXT_TYPE + COMMA_SEP +
                    ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL + INT_TYPE + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReminderSchema.TABLE_NAME;

    public static class ReminderSchema implements BaseColumns {
        public static final String TABLE_NAME = "reminders";
        public static final String COLUMN_NAME_CONTACT_NAME = "contact_name";
        public static final String COLUMN_NAME_CONTACT_ID = "contact_id";
        public static final String COLUMN_NAME_REMINDER_INTERVAL = "interval";
    }

}
