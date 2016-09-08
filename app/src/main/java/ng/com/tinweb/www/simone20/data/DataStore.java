package ng.com.tinweb.www.simone20.data;

import android.database.Cursor;

import java.util.List;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface DataStore {

    boolean save(String contactId, String contactName, int interval);
    boolean update(String contactId, String contactName, int interval);
    void getSingle(String contactId, ActionCallback callback);
    void getMultiple(ActionCallback callback);
    boolean delete(String contactId);

    interface ActionCallback {
        void onGetSuccess(String contactId, String contactname, int interval);
        void onGetError();
        List<Reminder> onGetMultipleSuccess();
        void onGetMultipleError();
    }

}
