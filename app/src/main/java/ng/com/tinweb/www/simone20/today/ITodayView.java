package ng.com.tinweb.www.simone20.today;

import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 02/09/2016.
 */
interface ITodayView {

    /**
     * Interface to interact with the presenter
     */

    void onRemindersLoaded(List<SimOneReminder> simOneReminders);
    void callContact(String contactName);
    void onReminderLoadingError(String message);
}
