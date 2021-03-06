package ng.com.tinweb.www.simone20.data.group;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ng.com.tinweb.www.simone20.data.BaseDbHelper;
import ng.com.tinweb.www.simone20.data.DbContract;

import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.DB_DELETE_ERROR;
import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.DB_INSERT_ERROR;
import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.GROUP_EXISTS_ERROR;
import static ng.com.tinweb.www.simone20.data.group.SimOneGroup.UNKNOWN_ERROR;

/**
 * GroupDbHelper - Database helper for group model
 */

class GroupDbHelper extends BaseDbHelper implements DataStore {

    GroupDbHelper(Context context) {
        super(context);
    }

    @Override
    public void save(String name, int interval, SimOneGroup.ActionCallback callback) {
        SQLiteDatabase database = getWritableDatabase();

        // Standardise group name for insertion
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        // CHECK IF GROUP EXISTS

        String[] projection = {
                DbContract.GroupSchema._ID,
                DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME,
        };
        String selection = DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME + " = ?";
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

        ContentValues values = new ContentValues();
        values.put(DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME, name);
        values.put(DbContract.GroupSchema.COLUMN_NAME_GROUP_INTERVAL, interval);

        if (cursor.getCount() == 0) {
            long count = database.insert(DbContract.GroupSchema.TABLE_NAME, null, values);
            if (count != 0) {
                callback.onSuccess();
            } else {
                callback.onError(DB_INSERT_ERROR);
            }
        } else {
            callback.onError(GROUP_EXISTS_ERROR);
            cursor.close();
        }
    }

    @Override
    public void update(String oldName, String name, int interval,
                       SimOneGroup.ActionCallback callback) {
        SQLiteDatabase database = getWritableDatabase();

        // Standardise group name for insertion
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        String[] projection = {
                DbContract.GroupSchema._ID,
                DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME,
        };
        String selection = DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME + " = ?";
        String[] selectionArgs = {name};
        String[] insertArgs = {oldName};

        if (!oldName.equals(name)) {

            Cursor cursor = database.query(
                    DbContract.GroupSchema.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor.getCount() > 0) {
                callback.onError(GROUP_EXISTS_ERROR);
                cursor.close();
                return;
            }

        }

        ContentValues values = new ContentValues();
        values.put(DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME, name);
        values.put(DbContract.GroupSchema.COLUMN_NAME_GROUP_INTERVAL, interval);

        long count = database.update(DbContract.GroupSchema.TABLE_NAME, values, selection,
                insertArgs);
        if (count != 0) {
            callback.onSuccess();
            // TODO: 18/04/2017 run update mechanism for reminders in this group
        } else {
            callback.onError(DB_INSERT_ERROR);
        }

    }

    @Override
    public void getSingle(String name, SimOneGroup.ActionCallback callback) {

    }

    @Override
    public void getMultiple(SimOneGroup.GetAllCallback callback) {
        SQLiteDatabase database = getReadableDatabase();

        String[] projection = {
                DbContract.GroupSchema._ID,
                DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME,
                DbContract.GroupSchema.COLUMN_NAME_GROUP_MEMBERS,
                DbContract.GroupSchema.COLUMN_NAME_GROUP_INTERVAL
        };

        String sortOrder = DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME + " ASC";

        Cursor cursor = database.query(
                DbContract.GroupSchema.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        if (cursor != null) {
            List<SimOneGroup> groups = new ArrayList<>();
            while (cursor.moveToNext()) {
                int groupId = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DbContract.GroupSchema._ID)
                );
                String groupName = cursor.getString(
                        cursor.getColumnIndexOrThrow(DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME)
                );
                int groupMembers = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DbContract.GroupSchema.COLUMN_NAME_GROUP_MEMBERS)
                );
                int interval = cursor.getInt(
                        cursor.getColumnIndexOrThrow(DbContract.GroupSchema.COLUMN_NAME_GROUP_INTERVAL)
                );
                SimOneGroup simOneGroup = new SimOneGroup(context);
                simOneGroup.setName(groupName);
                simOneGroup.setMembers(groupMembers);
                simOneGroup.setInterval(interval);
                groups.add(simOneGroup);
            }
            callback.onSuccess(groups);
            cursor.close();
        } else {
            callback.onError(UNKNOWN_ERROR);
        }
    }

    @Override
    public void delete(String name, SimOneGroup.ActionCallback callback) {
        SQLiteDatabase database = getWritableDatabase();
        String selection = DbContract.GroupSchema.COLUMN_NAME_GROUP_NAME + " = ?";
        String[] selectionArgs = {name};

        long count = database.delete(DbContract.GroupSchema.TABLE_NAME, selection,
                selectionArgs);

        // TODO: 19/04/2017 perform clean up task for all reminders with the group

        if (count != 0) callback.onSuccess();

        else callback.onError(DB_DELETE_ERROR);
    }

    @Override
    public int getInterval(String groupName) {
        return 0;
    }
}
