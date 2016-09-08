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
        int weeklyRemindersCount = 2;
        if (reminderView.get() != null)
            reminderView.get().setWeeklyReminders(weeklyRemindersCount);
    }
}
