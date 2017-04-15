package ng.com.tinweb.www.simone20.today;

import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 14/04/2017.
 */

class TodayContract {

    /**
     * Interface to interact with the presenter
     */
    interface View {

        void onRemindersLoaded(List<SimOneReminder> simOneReminders);

        void callContact(String contactName);

        void onReminderLoadingError(String message);
    }

    interface Presenter {

        void loadReminders();

        void callContact(String contactName);
    }
}
