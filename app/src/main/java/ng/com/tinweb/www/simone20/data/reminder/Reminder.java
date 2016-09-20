package ng.com.tinweb.www.simone20.data.reminder;

import android.content.Context;

import ng.com.tinweb.www.simone20.SimOne;
import ng.com.tinweb.www.simone20.data.BaseDbHelper;

/**
 * Created by kamiye on 08/09/2016.
 */
public class Reminder {

    private ReminderDataStore reminderDataStore;
    private String contactId;
    private String contactName;
    private String contactGroup;
    private int interval;

    public Reminder() {
        initialiseDataStore();
    }

    public Reminder(String contactId, String contactName, String contactGroup, int interval) {
        super();
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactGroup = contactGroup;
        this.interval = interval;
    }

    public boolean create() {
        return reminderDataStore.save(contactId, contactName, contactGroup, interval);
    }

    public boolean update() {
        return reminderDataStore.update(contactId, contactName, interval);
    }

    public boolean remove(String contactId) {
        return reminderDataStore.delete(contactId);
    }

    public void get(ReminderDataStore.ActionCallback callback) {
        reminderDataStore.getSingle(contactId, callback);
    }

    public void getAll(ReminderDataStore.ActionCallback callback) {
        reminderDataStore.getMultiple(callback);
    }

    public String getContactId() {
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

    public void setContactId(String contactId) {
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
        reminderDataStore = new ReminderDb(context);
    }
}
