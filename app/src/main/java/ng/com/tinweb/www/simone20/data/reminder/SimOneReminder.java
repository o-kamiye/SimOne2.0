package ng.com.tinweb.www.simone20.data.reminder;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.simone20.data.contact.SimOneContact;

/**
 * Created by kamiye on 08/09/2016.
 */
public class SimOneReminder extends SimOneContact {

    private DataStore dataStore;
    private String contactName;
    private String contactGroup;
    private int contactId;
    private int interval;
    private int daysLeft;

    public SimOneReminder(Context context) {
        super(context);
        initialiseDataStore(context);
    }

    public void set(boolean isUpdate, ActionCallback callback) {
        dataStore.save(contactId, contactGroup, interval, isUpdate, callback);
    }

    public boolean remove(int contactId) {
        return dataStore.delete(contactId);
    }

    public void get(GetSingleCallback callback) {
        dataStore.getSingle(contactId, callback);
    }

    public void getAll(boolean isToday, GetAllCallback callback) {
        dataStore.getMultiple(isToday, callback);
    }

    public int getReminderContactId() {
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

    public int getDaysLeft() {
        return daysLeft;
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

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    private void initialiseDataStore(Context context) {
        dataStore = new ReminderDbHelper(context);
    }

    public interface ActionCallback {
        void onSuccess();
        void onError(int errorCode);
    }

    public interface GetSingleCallback {
        void onSuccess(String contactName, int interval);
        void onError(int errorCode);
    }

    public interface GetAllCallback {
        void onSuccess(HashMap<String, String> metaData, List<SimOneReminder> simOneReminders);
        void onError(int errorCode);
    }

    public static class Builder {

        private String contactName;
        private String contactGroup;
        private int contactId;
        private int interval;
        private int daysLeft;
        private SimOneReminder simOneReminder;

        public Builder(Context context) {

            simOneReminder = new SimOneReminder(context);

        }

        public Builder setContactId(int contactId) {
            this.contactId = contactId;
            return this;
        }

        public Builder setContactName(String contactName) {
            this.contactName = contactName;
            return this;
        }

        public Builder setContactGroup(String contactGroup) {
            this.contactGroup = contactGroup;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setDaysLeft(int daysLeft) {
            this.daysLeft = interval;
            return this;
        }

        public SimOneReminder create() {
            simOneReminder.setContactId(contactId);
            simOneReminder.setContactGroup(contactName);
            simOneReminder.setContactGroup(contactGroup);
            simOneReminder.setInterval(interval);
            simOneReminder.setDaysLeft(daysLeft);

            return simOneReminder;
        }

    }

}
