package ng.com.tinweb.www.simone20.reminder;

/**
 * Created by kamiye on 08/09/2016.
 */
interface IReminderPresenter {

    void loadReminders();

    void deleteReminder(String contactId);

    interface IReminderFragmentPresenter {
        void setReminder(String contactGroup, int interval, boolean isUpdate);
    }
}
