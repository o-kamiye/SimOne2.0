package ng.com.tinweb.www.simone20.today;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 04/09/2016.
 */
class TodayPresenter implements TodayContract.Presenter {

    private WeakReference<TodayContract.View> view;
    private SimOneReminder reminder;

    @Inject
    TodayPresenter(SimOneReminder reminder, TodayContract.View view) {
        this.view = new WeakReference<>(view);
        this.reminder = reminder;
    }

    @Override
    public void loadReminders() {
        reminder.getAll(true, new SimOneReminder.GetAllCallback() {
            @Override
            public void onSuccess(HashMap<String, String> metaData,
                                  List<SimOneReminder> reminders) {
                if (view.get() != null) {
                    view.get().onRemindersLoaded(reminders);
                }
            }

            @Override
            public void onError(int errorCode) {
                if (view.get() != null) {
                    String message = "An unknown error occurred";
                    view.get().onReminderLoadingError(message);
                }
            }
        });
    }

}
