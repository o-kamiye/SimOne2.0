package ng.com.tinweb.www.simone20.today;

import java.lang.ref.WeakReference;

/**
 * Created by kamiye on 04/09/2016.
 */
public class TodayPresenter implements ITodayPresenter {

    private WeakReference<ITodayView> todayView;

    public TodayPresenter(ITodayView todayView) {
        this.todayView = new WeakReference<>(todayView);
    }

    @Override
    public void setReminderCount() {
        int remindersCount = 0;
        if (todayView.get() != null)
            todayView.get().setTotalReminders(remindersCount);
    }

    @Override
    public void callContact(String contactName) {
        if (todayView.get() != null )
            todayView.get().callContact(contactName);
    }

}
