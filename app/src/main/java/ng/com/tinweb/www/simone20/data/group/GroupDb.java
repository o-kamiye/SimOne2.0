package ng.com.tinweb.www.simone20.data.group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.BaseDbHelper;
import ng.com.tinweb.www.simone20.data.DbContract;

import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.DB_INSERT_ERROR;
import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.GROUP_EXISTS_ERROR;

/**
 * Created by kamiye on 20/09/2016.
 */

class GroupDb extends BaseDbHelper implements DataStore {

    GroupDb(Context context) {
        super(context);
    }

    @Override
    public void save(String name, int interval, SimOneGroup.ActionCallback callback) {
        SQLiteDatabase database = getWritableDatabase();

        // Standardise group name for insertion
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        // CHECK IF GROUP EXISTS

        String[] projection = {
                DbContract.GroupSchema._ID,
                DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME,
        };
        String selection = DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME+ " = ?";
        String[] selectionArgs = {name};

        Cursor cursor = database.query(
                DbContract.GroupSchema.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME, name);
            values.put(DbContract.GroupSchema.COLUMN_NAME_GROUP_INTERVAL, interval);

            long count = database.insert(DbContract.GroupSchema.TABLE_NAME, null, values);
            if (count != 0) {
                callback.onSuccess();
            }
            else {
                callback.onError(DB_INSERT_ERROR);
            }
        }
        else {
            callback.onError(GROUP_EXISTS_ERROR);
            cursor.close();
        }
    }

    @Override
    public boolean update(String name, String members, int interval) {
        return false;
    }

    @Override
    public void getSingle() {

    }

    @Override
    public void getMultiple() {

    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public int getInterval(String groupName) {
        return 0;
    }
}
