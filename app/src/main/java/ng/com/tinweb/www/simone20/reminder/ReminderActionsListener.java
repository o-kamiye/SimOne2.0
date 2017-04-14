package ng.com.tinweb.www.simone20.reminder;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface ReminderActionsListener {
    void onEditAction(SimOneReminder simOneReminder);
    void onDeleteAction(SimOneReminder simOneReminder);
}
