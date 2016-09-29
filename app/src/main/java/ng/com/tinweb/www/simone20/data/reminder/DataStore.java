package ng.com.tinweb.www.simone20.data.reminder;

import java.util.List;

/**
 * Created by kamiye on 08/09/2016.
 */
interface DataStore {

    boolean save(int contactId, String contactGroupId, int interval, boolean newSave);
    void getSingle(int contactId, ActionCallback callback);
    void getMultiple(ActionCallback callback);
    boolean delete(int contactId);

    interface ActionCallback {
        void onGetSuccess(String contactname, int interval);
        void onGetError();
        void onGetMultipleSuccess(List<Reminder> reminders);
        void onGetMultipleError();
    }

}
