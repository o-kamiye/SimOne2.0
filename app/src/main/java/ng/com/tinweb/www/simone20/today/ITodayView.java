package ng.com.tinweb.www.simone20.today;

import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

/**
 * Created by kamiye on 02/09/2016.
 */
interface ITodayView {

    /**
     * Interface to interact with the presenter
     */

    void onRemindersLoaded(List<Reminder> reminders);
    void callContact(String contactName);
    void onReminderLoadingError(String message);
}
