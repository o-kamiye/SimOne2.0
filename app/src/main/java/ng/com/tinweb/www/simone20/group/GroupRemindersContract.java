package ng.com.tinweb.www.simone20.group;

import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * GroupRemindersContract -
 */
public class GroupRemindersContract {

    interface View {

        /**
         * Method to notify view of successful reminders loaded
         */
        void onRemindersLoaded(List<SimOneReminder> reminders);

        /**
         * Method to notify view of reminder loading error
         *
         * @param errorCode Error code
         */
        void onRemindersLoadingError(int errorCode);

    }

    interface Presenter {

        /**
         * Method to load reminders for the group
         *
         * @param groupName Group name
         */
        void loadGroupReminders(String groupName);
    }
}
