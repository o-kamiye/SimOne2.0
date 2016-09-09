package ng.com.tinweb.www.simone20.reminder;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface IReminderPresenter {
    void setWeeklyReminderCount();

    void editReminder(String contactId);

    void deleteReminder(String contactId);
}
