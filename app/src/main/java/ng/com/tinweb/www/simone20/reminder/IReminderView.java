package ng.com.tinweb.www.simone20.reminder;

/**
 * Created by kamiye on 08/09/2016.
 */
public interface IReminderView {
    void setWeeklyReminders(int total);

    void showEditReminderPopUp();

    void showDeleteSuccessInfo();

    void showDeleteErrorInfo();
}
