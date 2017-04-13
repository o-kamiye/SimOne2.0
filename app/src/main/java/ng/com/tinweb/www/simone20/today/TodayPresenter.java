package ng.com.tinweb.www.simone20.today;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 04/09/2016.
 */
class TodayPresenter implements ITodayPresenter {

    private WeakReference<ITodayView> todayView;
    private SimOneReminder simOneReminder;

    TodayPresenter(SimOneReminder simOneReminder, ITodayView todayView) {
        this.todayView = new WeakReference<>(todayView);
        this.simOneReminder = simOneReminder;
    }

    @Override
    public void loadReminders() {
        simOneReminder.getAll(true, new SimOneReminder.GetAllCallback() {
            @Override
            public void onSuccess(HashMap<String, String> metaData, List<SimOneReminder> simOneReminders) {
                if (todayView.get() != null) {
                    todayView.get().onRemindersLoaded(simOneReminders);
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
