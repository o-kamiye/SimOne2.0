package ng.com.tinweb.www.simone20.data.reminder;


/**
 * Created by kamiye on 08/09/2016.
 */
interface DataStore {

    void save(int contactId, String contactGroupId, int interval, boolean newSave, Reminder.ActionCallback callback);
    void getSingle(int contactId, Reminder.GetSingleCallback callback);
    void getMultiple(boolean isToday, Reminder.GetAllCallback callback);
    boolean delete(int contactId);

}
