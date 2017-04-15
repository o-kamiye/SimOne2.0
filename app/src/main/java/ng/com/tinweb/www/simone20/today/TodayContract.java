package ng.com.tinweb.www.simone20.today;

import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 14/04/2017.
 */

class TodayContract {

    interface View {

        /**
         * Callback to notify view of loaded reminders
         * @param reminders loaded reminders
         */
        void onRemindersLoaded(List<SimOneReminder> reminders);

        /**
         * Callback do display reminder loading error
         * @param message Error message
         */
        void onReminderLoadingError(String message);
    }

    interface Presenter {

        /**
         * Get reminders from database
         */
        void loadReminders();

    }
}
