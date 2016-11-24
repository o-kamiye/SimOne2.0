package ng.com.tinweb.www.simone20.today;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

/**
 * Created by kamiye on 04/09/2016.
 */
public class TodayPresenter implements ITodayPresenter {

    private WeakReference<ITodayView> todayView;
    private Reminder reminder;

    public TodayPresenter(Reminder reminder, ITodayView todayView) {
        this.todayView = new WeakReference<>(todayView);
        this.reminder = reminder;
    }

    @Override
    public void loadReminders() {
        reminder.getAll(true, new Reminder.GetAllCallback() {
            @Override
            public void onSuccess(HashMap<String, String> metaData, List<Reminder> reminders) {
                if (todayView.get() != null) {
                    todayView.get().onRemindersLoaded(reminders);
                }
            }

            @Override
            public void onError(int errorCode) {
                if (todayView.get() != null) {
                    String message = "An unknown error occurred";
                    todayView.get().onReminderLoadingError(message);
                }
            }
        });
    }

    @Override
    public void callContact(String contactName) {
        if (todayView.get() != null )
            todayView.get().callContact(contactName);
    }

}
