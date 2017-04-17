package ng.com.tinweb.www.simone20.reminder;

import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 14/04/2017.
 */

class ReminderContract {

    interface View {

        void onRemindersLoaded(List<SimOneReminder> simOneReminders);

        void onReminderLoadingError();

        void setWeekReminderTextView(int total);

        void showDeleteSuccessInfo();

        void showDeleteErrorInfo();

    }

    interface Presenter {

        void loadReminders();

        void deleteReminder(int contactId);

    }
}
