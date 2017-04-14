package ng.com.tinweb.www.simone20.data.reminder;


/**
 * Created by kamiye on 08/09/2016.
 */
interface DataStore {

    void save(int contactId, String contactGroupId, int interval, boolean newSave, SimOneReminder.ActionCallback callback);
    void getSingle(int contactId, SimOneReminder.GetSingleCallback callback);
    void getMultiple(boolean isToday, SimOneReminder.GetAllCallback callback);
    boolean delete(int contactId);

}
