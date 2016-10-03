package ng.com.tinweb.www.simone20.data.reminder;

import android.content.Context;

import ng.com.tinweb.www.simone20.SimOne;

/**
 * Created by kamiye on 08/09/2016.
 */
public class Reminder {

    private DataStore dataStore;
    private int contactId;
    private String contactName;
    private String contactGroup;
    private int interval;

    public Reminder() {
        initialiseDataStore();
    }

    public Reminder (int contactId, String contactName) {
        super();
        this.contactId = contactId;
        this.contactName = contactName;
    }

    public Reminder(int contactId, String contactName, String contactGroup, int interval) {
        super();
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactGroup = contactGroup;
        this.interval = interval;
    }

    public boolean create() {
        return dataStore.save(contactId, contactGroup, interval, true);
    }

    public boolean update() {
        return dataStore.save(contactId, contactName, interval, false);
    }

    public boolean remove(int contactId) {
        return dataStore.delete(contactId);
    }

    public void get(DataStore.ActionCallback callback) {
        dataStore.getSingle(contactId, callback);
    }

    public void getAll(DataStore.ActionCallback callback) {
        dataStore.getMultiple(callback);
    }

    public int getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactGroup() {
        return contactGroup;
    }

    public int getInterval() {
        return interval;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactGroup(String contactGroup) {
        this.contactGroup = contactGroup;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    private void initialiseDataStore() {
        Context context = SimOne.getContext();
        dataStore = new ReminderDbHelper(context);
    }
}
