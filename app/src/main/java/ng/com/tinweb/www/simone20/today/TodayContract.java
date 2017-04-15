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
