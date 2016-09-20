package ng.com.tinweb.www.simone20.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiye on 08/09/2016.
 */
public class ReminderDbHelper extends SQLiteOpenHelper implements ReminderDataStore {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SimOne.db";

    public ReminderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ReminderContract.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(ReminderContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public boolean save(String contactId, String contactName, int interval) {
        SQLiteDatabase database = getWritableDatabase();

        // TODO add date calculation to the mix

        ContentValues values = new ContentValues();
        values.put(ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_ID,
                contactId);
        values.put(ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME,
                contactName);
        values.put(ReminderContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL,
                interval);

        long rowId = database.insert(ReminderContract.ReminderSchema.TABLE_NAME, null, values);
        return rowId != 0;
    }

    @Override
    public boolean update(String contactId, String contactName, int interval) {
        SQLiteDatabase database = getWritableDatabase();

        // TODO add date calculation to the mix

        ContentValues values = new ContentValues();
        values.put(ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_ID,
                contactId);
        values.put(ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME,
                contactName);
        values.put(ReminderContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL,
                interval);

        String selection = ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_ID + " = ?";
        String[] selectionArgs = {contactId};

        int count = database.update(ReminderContract.ReminderSchema.TABLE_NAME,
                values, selection, selectionArgs);

        return count != 0;
    }

    @Override
    public void getSingle(String contactId, ActionCallback callback) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                ReminderContract.ReminderSchema._ID,
                ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_ID,
                ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME,
                ReminderContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL,
                ReminderContract.ReminderSchema.COLUMN_NAME_DATE_DUE
        };
        String selection = ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_ID + " = ?";
        String[] selectionArgs = {contactId};

        Cursor cursor = database.query(
                ReminderContract.ReminderSchema.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            String contactName = cursor.getString(
                    cursor.getColumnIndexOrThrow(ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME)
            );
            int interval = cursor.getInt(
                    cursor.getColumnIndexOrThrow(ReminderContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL)
            );
            String dueDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(ReminderContract.ReminderSchema.COLUMN_NAME_DATE_DUE)
            );
            callback.onGetSuccess(contactId, contactName, interval);
        }
        else {
            callback.onGetError();
        }
        cursor.close();
    }

    @Override
    public void getMultiple(ActionCallback callback) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                ReminderContract.ReminderSchema._ID,
                ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_ID,
                ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME,
                ReminderContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL,
                ReminderContract.ReminderSchema.COLUMN_NAME_DATE_DUE
        };

        String sortOrder = ReminderContract.ReminderSchema.COLUMN_NAME_DATE_DUE + " ASC";

        Cursor cursor = database.query(
                ReminderContract.ReminderSchema.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        if (cursor.moveToFirst()) {
            List<Reminder> reminders = new ArrayList<>();
            while(cursor.moveToNext()) {
                String contactId = cursor.getString(
                        cursor.getColumnIndexOrThrow(ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_ID)
                );
                String contactName = cursor.getString(
                        cursor.getColumnIndexOrThrow(ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME)
                );
                int interval = cursor.getInt(
                        cursor.getColumnIndexOrThrow(ReminderContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL)
                );
                String dueDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(ReminderContract.ReminderSchema.COLUMN_NAME_DATE_DUE)
                );
                reminders.add(new Reminder(contactId, contactName, interval));
            }
            callback.onGetMultipleSuccess(reminders);
        }
        else {
            callback.onGetMultipleError();
        }
        cursor.close();
    }

    @Override
    public boolean delete(String contactId) {
        SQLiteDatabase database = getWritableDatabase();

        String selection = ReminderContract.ReminderSchema.COLUMN_NAME_CONTACT_ID + " = ?";
        String[] selectionArgs = {contactId};
        int count = database.delete(ReminderContract.ReminderSchema.TABLE_NAME,
                selection, selectionArgs);

        return count != 0;
    }
}