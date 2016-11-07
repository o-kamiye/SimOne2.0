package ng.com.tinweb.www.simone20.reminder;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface ReminderActionsListener {
    void onEditAction(Reminder reminder);
    void onDeleteAction(Reminder reminder);
}
