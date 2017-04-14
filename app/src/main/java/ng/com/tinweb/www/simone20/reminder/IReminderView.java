package ng.com.tinweb.www.simone20.reminder;

import java.util.List;
import java.util.Map;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 08/09/2016.
 */
interface IReminderView {

    void onRemindersLoaded(List<SimOneReminder> simOneReminders);

    void onReminderLoadingError();

    void setWeekReminderTextView(int total);

    void showDeleteSuccessInfo();

    void showDeleteErrorInfo();

    interface IReminderFragmentView {

        void onSetReminderSuccess();

        void onSetReminderError(String message);

        void onGroupNamesLoaded(Map<String, Integer> groupsMap);

        void onGroupNamesLoadingError(String message);
    }
}
