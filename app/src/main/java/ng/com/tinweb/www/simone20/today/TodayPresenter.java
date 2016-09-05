package ng.com.tinweb.www.simone20.today;

import android.widget.Toast;

import java.lang.ref.WeakReference;

import ng.com.tinweb.www.simone20.SimOneApplication;

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
        Toast.makeText(SimOneApplication.getContext(), "I am going to call " + contactName, Toast.LENGTH_LONG)
                .show();
    }

}
