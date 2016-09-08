package ng.com.tinweb.www.simone20.data;

import android.content.Context;

import ng.com.tinweb.www.simone20.SimOneApplication;

/**
 * Created by kamiye on 08/09/2016.
 */
public class Reminder {

    private DataStore dataStore;
    private String contactId;
    private String contactName;
    private int interval;

    public Reminder() {
        initialiseDataStore();
    }

    public Reminder(String contactId, String contactName, int interval) {
        super();
        this.contactId = contactId;
        this.contactName = contactName;
        this.interval = interval;
    }

    public boolean create() {
        return dataStore.save(contactId, contactName, interval);
    }

    public boolean update() {
        return dataStore.update(contactId, contactName, interval);
    }

    public void remove() {
        if (contactId == null)
            throw new NullPointerException("Contact Id needs to be set before remove method is called");
        dataStore.delete(contactId);
    }

    public void get(DataStore.ActionCallback callback) {
        if (contactId == null)
            throw new NullPointerException("Contact Id needs to be set before getSingle method is called");
        dataStore.getSingle(contactId, callback);
    }

    public void getAll(DataStore.ActionCallback callback) {
        dataStore.getMultiple(callback);
    }

    public String getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public int getInterval() {
        return interval;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    private void initialiseDataStore() {
        Context context = SimOneApplication.getContext();
        dataStore = new ReminderDbHelper(context);
    }
}
