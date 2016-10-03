package ng.com.tinweb.www.simone20.helper;



import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.data.reminder.Reminder;

/**
 * Created by kamiye on 29/09/2016.
 */

public class Injection {

    public static SimOneContact getSimOneContact() {
        return new SimOneContact();
    }

    public static Reminder getReminder(int contactId, String contactName) {
        return new Reminder(contactId, contactName);
    }

}
