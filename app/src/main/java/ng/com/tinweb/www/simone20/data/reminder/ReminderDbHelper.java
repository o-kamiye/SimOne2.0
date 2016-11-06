package ng.com.tinweb.www.simone20.data.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.BaseDbHelper;
import ng.com.tinweb.www.simone20.data.DbContract;

/**
 * Created by kamiye on 20/09/2016.
 */

class ReminderDbHelper extends BaseDbHelper implements DataStore {

    public static int UNKNOWN_ERROR = 13;

    private Context context;

    ReminderDbHelper(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void save(int contactId, String contactGroupId,
                        int interval, boolean newSave, Reminder.ActionCallback callback) {
        SQLiteDatabase database = getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, interval);
        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.date_format),
                Locale.ENGLISH);
        String dueDate = sdf.format(calendar.getTime());

        ContentValues values = new ContentValues();
        values.put(DbContract.ContactSchema.COLUMN_NAME_DATE_DUE, dueDate);
        if (contactGroupId != null) {
            values.put(DbContract.ContactSchema.COLUMN_NAME_CONTACT_GROUP,
                    contactGroupId);
        }
        if (interval != 0) {
            values.put(DbContract.ContactSchema.COLUMN_NAME_REMINDER_INTERVAL,
                    interval);
        }
        // TODO don't mess up duedate when updating a reminder.
        // TODO check here first!!!
        if (newSave) {
            values.put(DbContract.ContactSchema.COLUMN_NAME_REMINDER_ACTIVATED,
                    DbContract.TRUE);
        }

        String selection = DbContract.ContactSchema._ID + " = ?";
        String[] selectionArgs = {String.valueOf(contactId)};

        int count = database.update(DbContract.ContactSchema.TABLE_NAME,
                values, selection, selectionArgs);

        if (count != 0) {
            callback.onSuccess();
        }
        else {
            callback.onError(UNKNOWN_ERROR);
        }
    }

    @Override
    public void getSingle(int contactId, ActionCallback callback) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                DbContract.ContactSchema._ID,
                DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME,
                DbContract.ContactSchema.COLUMN_NAME_CONTACT_NUMBERS,
                DbContract.ContactSchema.COLUMN_NAME_REMINDER_INTERVAL,
                DbContract.ContactSchema.COLUMN_NAME_DATE_DUE
        };
        String selection = DbContract.ContactSchema._ID + " = ?"
                + " AND " + DbContract.ContactSchema.COLUMN_NAME_REMINDER_ACTIVATED + " = ?";
        String[] selectionArgs = {
                String.valueOf(contactId),
                String.valueOf(DbContract.TRUE)
        };

        Cursor cursor = database.query(
                DbContract.ContactSchema.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            String contactName = cursor.getString(
                    cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME)
            );
            int interval = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_REMINDER_INTERVAL)
            );
            String dueDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_DATE_DUE)
            );
            callback.onGetSuccess(contactName, interval);
        } else {
            callback.onGetError();
        }
        cursor.close();
    }

    @Override
    public void getMultiple(ActionCallback callback) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                DbContract.ContactSchema._ID,
                DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME,
                DbContract.ContactSchema.COLUMN_NAME_CONTACT_NUMBERS,
                DbContract.ContactSchema.COLUMN_NAME_REMINDER_INTERVAL,
                DbContract.ContactSchema.COLUMN_NAME_DATE_DUE
        };

        String selection = DbContract.ContactSchema.COLUMN_NAME_REMINDER_ACTIVATED + " = ?";
        String[] selectionArgs = {String.valueOf(DbContract.TRUE)};
        String sortOrder = DbContract.ContactSchema.COLUMN_NAME_DATE_DUE + " ASC";

        Cursor cursor = database.query(
                DbContract.ContactSchema.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        if (cursor != null) {
            List<Reminder> reminders = new ArrayList<>();
            while (cursor.moveToNext()) {
                int contactId = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DbContract.ContactSchema._ID)
                );
                String contactName = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME)
                );
                String contactGroup = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_CONTACT_GROUP)
                );
                int interval = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_REMINDER_INTERVAL)
                );
                String dueDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_DATE_DUE)
                );
                reminders.add(new Reminder(contactId, contactName, contactGroup, interval));
            }
            callback.onGetMultipleSuccess(reminders);
            cursor.close();
        } else {
            callback.onGetMultipleError();
        }
    }

    @Override
    public boolean delete(int contactId) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbContract.ContactSchema.COLUMN_NAME_CONTACT_GROUP, "");
        values.put(DbContract.ContactSchema.COLUMN_NAME_REMINDER_INTERVAL,
                DbContract.FALSE);
        values.put(DbContract.ContactSchema.COLUMN_NAME_REMINDER_ACTIVATED,
                DbContract.FALSE);

        String selection = DbContract.ContactSchema._ID + " = ?";
        String[] selectionArgs = {String.valueOf(contactId)};

        int count = database.update(DbContract.ContactSchema.TABLE_NAME,
                values, selection, selectionArgs);

        return count != 0;
    }
}
