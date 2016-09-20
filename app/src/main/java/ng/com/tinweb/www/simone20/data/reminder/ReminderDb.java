package ng.com.tinweb.www.simone20.data.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ng.com.tinweb.www.simone20.data.BaseDbHelper;
import ng.com.tinweb.www.simone20.data.DbContract;

/**
 * Created by kamiye on 20/09/2016.
 */

public class ReminderDb extends BaseDbHelper implements ReminderDataStore {

    public ReminderDb(Context context) {
        super(context);
    }

    @Override
    public boolean save(String contactId, String contactName, String contactGroupId, int interval) {
        SQLiteDatabase database = getWritableDatabase();

        // TODO add date calculation to the mix

        ContentValues values = new ContentValues();
        values.put(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_ID,
                contactId);
        values.put(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME,
                contactName);
        values.put(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_GROUP,
                contactGroupId);
        values.put(DbContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL,
                interval);

        long rowId = database.insert(DbContract.ReminderSchema.TABLE_NAME, null, values);
        return rowId != 0;
    }

    @Override
    public boolean update(String contactId, String contactName, int interval) {
        SQLiteDatabase database = getWritableDatabase();

        // TODO add date calculation to the mix

        ContentValues values = new ContentValues();
        values.put(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_ID,
                contactId);
        values.put(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME,
                contactName);
        values.put(DbContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL,
                interval);

        String selection = DbContract.ReminderSchema.COLUMN_NAME_CONTACT_ID + " = ?";
        String[] selectionArgs = {contactId};

        int count = database.update(DbContract.ReminderSchema.TABLE_NAME,
                values, selection, selectionArgs);

        return count != 0;
    }

    @Override
    public void getSingle(String contactId, ActionCallback callback) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                DbContract.ReminderSchema._ID,
                DbContract.ReminderSchema.COLUMN_NAME_CONTACT_ID,
                DbContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME,
                DbContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL,
                DbContract.ReminderSchema.COLUMN_NAME_DATE_DUE
        };
        String selection = DbContract.ReminderSchema.COLUMN_NAME_CONTACT_ID + " = ?";
        String[] selectionArgs = {contactId};

        Cursor cursor = database.query(
                DbContract.ReminderSchema.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            String contactName = cursor.getString(
                    cursor.getColumnIndexOrThrow(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME)
            );
            int interval = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DbContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL)
            );
            String dueDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(DbContract.ReminderSchema.COLUMN_NAME_DATE_DUE)
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
                DbContract.ReminderSchema._ID,
                DbContract.ReminderSchema.COLUMN_NAME_CONTACT_ID,
                DbContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME,
                DbContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL,
                DbContract.ReminderSchema.COLUMN_NAME_DATE_DUE
        };

        String sortOrder = DbContract.ReminderSchema.COLUMN_NAME_DATE_DUE + " ASC";

        Cursor cursor = database.query(
                DbContract.ReminderSchema.TABLE_NAME,
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
                        cursor.getColumnIndexOrThrow(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_ID)
                );
                String contactName = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_NAME)
                );
                String contactGroup = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.ReminderSchema.COLUMN_NAME_CONTACT_GROUP)
                );
                int interval = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DbContract.ReminderSchema.COLUMN_NAME_REMINDER_INTERVAL)
                );
                String dueDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.ReminderSchema.COLUMN_NAME_DATE_DUE)
                );
                reminders.add(new Reminder(contactId, contactName, contactGroup, interval));
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

        String selection = DbContract.ReminderSchema.COLUMN_NAME_CONTACT_ID + " = ?";
        String[] selectionArgs = {contactId};
        int count = database.delete(DbContract.ReminderSchema.TABLE_NAME,
                selection, selectionArgs);

        return count != 0;
    }
}
