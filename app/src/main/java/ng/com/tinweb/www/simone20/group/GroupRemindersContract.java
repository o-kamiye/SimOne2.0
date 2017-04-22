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

        void onReminderRemoved();

        /**
         * Method to notify view of an error
         *
         * @param errorCode Error code
         */
        void onError(int errorCode);
    }

    interface Presenter {

        /**
         * Method to load reminders for the group
         *
         * @param groupName Group name
         */
        void loadGroupReminders(String groupName);

        /**
         * Method to remove reminder from a group
         *
         * @param contactId reminder's contact id
         */
        void removeContact(long contactId);
    }
}
