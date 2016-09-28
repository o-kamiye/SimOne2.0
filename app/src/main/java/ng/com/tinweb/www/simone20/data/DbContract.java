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

    public static final int FALSE = 0;
    public static final int TRUE = 1;

    static final String SQL_CREATE_REMINDER_TABLE =
            "CREATE TABLE " + ContactSchema.TABLE_NAME + " (" +
                    ContactSchema._ID + " INTEGER PRIMARY KEY," +
                    ContactSchema.COLUMN_NAME_CONTACT_ID + TEXT_TYPE + COMMA_SEP +
                    ContactSchema.COLUMN_NAME_CONTACT_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactSchema.COLUMN_NAME_CONTACT_NUMBERS + TEXT_TYPE + COMMA_SEP +
                    ContactSchema.COLUMN_NAME_CONTACT_GROUP + TEXT_TYPE + COMMA_SEP +
                    ContactSchema.COLUMN_NAME_REMINDER_ACTIVATED + INT_TYPE + " DEFAULT " + FALSE + COMMA_SEP +
                    ContactSchema.COLUMN_NAME_REMINDER_INTERVAL + INT_TYPE + " DEFAULT " + FALSE + COMMA_SEP +
                    ContactSchema.COLUMN_NAME_DATE_DUE + TEXT_TYPE + " )";

    static final String SQL_CREATE_GROUP_TABLE =
            "CREATE TABLE " + GroupSchema.TABLE_NAME + " (" +
                    GroupSchema._ID + " INTEGER PRIMARY KEY," +
                    GroupSchema.COLUMN_NAME_GROUP_ID + TEXT_TYPE + COMMA_SEP +
                    GroupSchema.COLUMN_NAME_GROUP_NAME + TEXT_TYPE + COMMA_SEP +
                    GroupSchema.COLUMN_NAME_GROUP_MEMBERS + TEXT_TYPE + COMMA_SEP +
                    GroupSchema.COLUMN_NAME_GROUP_INTERVAL + INT_TYPE + " )";



    static final String SQL_DELETE_REMINDER_TABLE =
            "DROP TABLE IF EXISTS " + ContactSchema.TABLE_NAME;

    static final String SQL_DELETE_GROUP_TABLE =
            "DROP TABLE IF EXISTS " + GroupSchema.TABLE_NAME;


    public static class ContactSchema implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME_CONTACT_ID = "contact_id";
        public static final String COLUMN_NAME_CONTACT_NAME = "contact_name";
        public static final String COLUMN_NAME_CONTACT_NUMBERS = "contact_numbers";
        public static final String COLUMN_NAME_CONTACT_GROUP = "contact_group_id";
        public static final String COLUMN_NAME_REMINDER_ACTIVATED = "activated";
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
