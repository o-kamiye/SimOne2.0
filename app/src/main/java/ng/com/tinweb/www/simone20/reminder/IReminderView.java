package ng.com.tinweb.www.simone20.reminder;

import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

/**
 * Created by kamiye on 08/09/2016.
 */
interface IReminderView {

    void onRemindersLoaded(List<Reminder> reminders);

    void onReminderLoadingError();

    void setWeekReminderTextView(int total);

    void showDeleteSuccessInfo();

    void showDeleteErrorInfo();

    interface IReminderFragmentView {

        void onSetReminderSuccess();

        void onSetReminderError(String message);

        void onGroupNamesLoaded(List<String> groupNames);

        void onGroupNamesLoadingError(String message);
    }
}
