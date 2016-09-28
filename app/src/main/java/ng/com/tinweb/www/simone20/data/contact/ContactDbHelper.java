package ng.com.tinweb.www.simone20.data.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.LongSparseArray;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ng.com.tinweb.www.simone20.data.BaseDbHelper;
import ng.com.tinweb.www.simone20.data.DbContract;
import ng.com.tinweb.www.simone20.data.reminder.Reminder;

/**
 * Created by kamiye on 28/09/2016.
 */

class ContactDbHelper extends BaseDbHelper implements DataStore {

    ContactDbHelper(Context context) {
        super(context);
    }

    @Override
    public boolean save(LongSparseArray<SimOneContact> contacts) {
        SQLiteDatabase database = getWritableDatabase();

        int successfulInsert = 0;
        for (int i = 0; i < contacts.size(); i++) {
            SimOneContact contact = contacts.valueAt(i);
            ContentValues values = new ContentValues();
            values.put(DbContract.ContactSchema.COLUMN_NAME_CONTACT_ID,
                    contact.getContactId());
            values.put(DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME,
                    contact.getName());
            values.put(DbContract.ContactSchema.COLUMN_NAME_CONTACT_NUMBERS,
                    contact.getPhones());
            long row = database.insert(DbContract.ContactSchema.TABLE_NAME, null, values);
            if (row > 0) {
                successfulInsert++;
            }
        }
        database.close();
        return successfulInsert == contacts.size();
    }

    @Override
    public List<SimOneContact> search(String searchQuery) {
        List<SimOneContact> contacts = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        String[] projection = {
                DbContract.ContactSchema._ID,
                DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME,
        };

        String selection = DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME + " LIKE ?";
        String[] selectionArgs = { "%" + searchQuery + "%" };
        String sortOrder = DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME + " ASC";

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
            while (cursor.moveToNext()) {
                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DbContract.ContactSchema._ID)
                );
                String contactName = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.ContactSchema.COLUMN_NAME_CONTACT_NAME)
                );
                contacts.add(new SimOneContact(id, contactName));
            }
            cursor.close();
        }
        return contacts;
    }
}
