package ng.com.tinweb.www.simone20.data.reminder;


/**
 * DataStore - Interface that reminder db implementation must implement
 */
interface DataStore {

    void save(int contactId, String contactGroupId, int interval, boolean newSave, SimOneReminder.ActionCallback callback);
    void getSingle(int contactId, SimOneReminder.GetSingleCallback callback);
    void getMultiple(boolean isToday, SimOneReminder.GetAllCallback callback);
    void getMultipleInGroup(String groupName, SimOneReminder.GetAllCallback callback);
    boolean delete(int contactId);
    void deleteFromGroup(long contactId, SimOneReminder.ActionCallback callback);
}
