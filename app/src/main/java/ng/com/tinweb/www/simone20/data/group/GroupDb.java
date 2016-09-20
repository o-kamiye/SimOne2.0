package ng.com.tinweb.www.simone20.data.group;

import android.content.Context;

import ng.com.tinweb.www.simone20.data.BaseDbHelper;

/**
 * Created by kamiye on 20/09/2016.
 */

public class GroupDb extends BaseDbHelper implements GroupDataStore {


    public GroupDb(Context context) {
        super(context);
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean update() {
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
}
