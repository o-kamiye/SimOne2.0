package ng.com.tinweb.www.simone20.reminder;

import java.lang.ref.WeakReference;

/**
 * Created by kamiye on 08/09/2016.
 */
public class ReminderPresenter implements IReminderPresenter {

    private WeakReference<IReminderView> reminderView;

    public ReminderPresenter(IReminderView reminderView) {
        this.reminderView = new WeakReference<>(reminderView);
    }

    @Override
    public void setWeeklyReminderCount() {
        // TODO calling view's setWeeklyReminder() method should be done asynchronously
        int weeklyRemindersCount = 2;
        if (reminderView.get() != null)
            reminderView.get().setWeeklyReminders(weeklyRemindersCount);
    }

    @Override
    public void editReminder(String contactId) {
        if (reminderView.get() != null) {
            // TODO get the reminder from the contactId
            // TODO get the view to show the pop up dialog with the reminder details
            reminderView.get().showEditReminderPopUp();
        }
    }

    @Override
    public void deleteReminder(String contactId) {
        if (reminderView.get() != null) {
            // TODO get the reminder from the contactId
            // TODO call the remove method on the reminder object
            // TODO if the removal was successful, then call the view's remove successful callback method
            reminderView.get().showDeleteSuccessInfo();
            // TODO if the removal was unsuccessful, then call the view's remove unsuccessful callback method
            reminderView.get().showDeleteErrorInfo();
        }
    }

}
