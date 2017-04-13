package ng.com.tinweb.www.simone20.data.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.BaseDbHelper;
import ng.com.tinweb.www.simone20.data.DbContract;

/**
 * Created by kamiye on 20/09/2016.
 */

class ReminderDbHelper extends BaseDbHelper implements DataStore {

    private static int UNKNOWN_ERROR = 13;

    private static int DAY_DIVIDER = 24 * 60 * 60 * 1000;

    ReminderDbHelper(Context context) {
        super(context);
    }

    @Override
    public void save(int contactId, String contactGroupName,
                     int interval, boolean isEditMode, SimOneReminder.ActionCallback callback) {
        SQLiteDatabase database = getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, interval);
        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.date_format),
                Locale.ENGLISH);
        String dueDate = sdf.format(calendar.getTime());
        Log.d("date_due", dueDate);

        ContentValues values = new ContentValues();
        values.put(DbContract.ContactSchema.COLUMN_NAME_DATE_DUE, dueDate);
        if (contactGroupName != null) {
            values.put(DbContract.ContactSchema.COLUMN_NAME_CONTACT_GROUP,
                    contactGroupName);
        }
        if (interval != 0) {
            values.put(DbContract.ContactSchema.COLUMN_NAME_REMINDER_INTERVAL,
                    interval);
        }
        // TODO don't mess up duedate when updating a reminder.
        // TODO check here first!!!
        if (!isEditMode) {
            values.put(DbContract.ContactSchema.COLUMN_NAME_REMINDER_ACTIVATED,
                    DbContract.TRUE);
        }

        String selection = DbContract.ContactSchema._ID + " = ?";
        String[] selectionArgs = {String.valueOf(contactId)};

        int count = database.update(DbContract.ContactSchema.TABLE_NAME,
                values, selection, selectionArgs);

        if (count != 0) {
            callback.onSuccess();
        } else {
            callback.onError(UNKNOWN_ERROR);
        }
    }

    @Override
    public void getSingle(int contactId, SimOneReminder.GetSingleCallback callback) {
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
            callback.onSuccess(contactName, interval);
        } else {
            callback.onError(UNKNOWN_ERROR);
        }
        cursor.close();
    }

    @Override
    public void getMultiple(boolean isToday, SimOneReminder.GetAllCallback callback) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                DbContract.ContactSchema._ID,
                DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME,
                DbContract.ContactSchema.COLUMN_NAME_CONTACT_GROUP,
                DbContract.ContactSchema.COLUMN_NAME_REMINDER_INTERVAL,
                DbContract.ContactSchema.COLUMN_NAME_DATE_DUE
        };

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.date_format),
                Locale.ENGLISH);
        String currentDateString = sdf.format(calendar.getTime());

        String selectionExtra = (isToday) ? " AND " + DbContract.ContactSchema.COLUMN_NAME_DATE_DUE
                + " = ?" : "";
        List<String> selectionList = new ArrayList<>();
        selectionList.add(String.valueOf(DbContract.TRUE));
        if (isToday) {
            selectionList.add(currentDateString);
        }
        String selection = DbContract.ContactSchema.COLUMN_NAME_REMINDER_ACTIVATED + " = ?"
                + selectionExtra;

        String[] selectionArgs = new String[selectionList.size()];
        selectionArgs = selectionList.toArray(selectionArgs);

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
            List<SimOneReminder> simOneReminders = new ArrayList<>();
            HashMap<String, String> remindersMetaData = new HashMap<>();
            int dueWeekly = 0;
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
                String dateString = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_DATE_DUE)
                );
                int daysLeft = 0;
                try {
                    Date dueDate = sdf.parse(dateString);
                    Date currentDate = sdf.parse(currentDateString);
                    long difference = Math.abs(dueDate.getTime() - currentDate.getTime());
                    daysLeft = (int) difference / DAY_DIVIDER;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (daysLeft < 7) {
                    dueWeekly++;
                }
                SimOneReminder simOneReminder = new SimOneReminder.Builder(context)
                        .setContactId(contactId)
                        .setContactName(contactName)
                        .setContactGroup(contactGroup)
                        .setInterval(interval)
                        .setDaysLeft(daysLeft)
                        .create();

                simOneReminders.add(simOneReminder);
            }
            remindersMetaData.put("dueWeekly", String.valueOf(dueWeekly));
            callback.onSuccess(remindersMetaData, simOneReminders);
            cursor.close();
        } else {
            callback.onError(UNKNOWN_ERROR);
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
