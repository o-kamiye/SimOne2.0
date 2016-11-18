package ng.com.tinweb.www.simone20.reminder;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

/**
 * Created by kamiye on 08/09/2016.
 */
class ReminderPresenter implements IReminderPresenter {

    private WeakReference<IReminderView> reminderView;
    private Reminder reminder;

    ReminderPresenter(Reminder reminder, IReminderView reminderView) {
        this.reminder = reminder;
        this.reminderView = new WeakReference<>(reminderView);
    }

    @Override
    public void loadReminders() {
        reminder.getAll(new Reminder.GetAllCallback() {
            @Override
            public void onSuccess(HashMap<String, String> metaData, List<Reminder> reminders) {
                if (reminderView.get() != null) {
                    reminderView.get().onRemindersLoaded(reminders);
                    int totalDueThisWeek = Integer.valueOf(metaData.get("dueWeekly"));
                    reminderView.get().setWeekReminderTextView(totalDueThisWeek);
                }
            }

            @Override
            public void onError(int errorCode) {
                if (reminderView.get() != null) {
                    reminderView.get().onReminderLoadingError();
                }
            }
        });
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

    static class SetReminderPresenter implements IReminderFragmentPresenter {

        private WeakReference<IReminderView.IReminderFragmentView> fragmentView;
        private Reminder reminder;

        SetReminderPresenter(IReminderView.IReminderFragmentView fragmentView,
                             Reminder reminder) {
            this.fragmentView = new WeakReference<>(fragmentView);
            this.reminder = reminder;
        }

        @Override
        public void setReminder(String contactGroup, int interval, boolean isUpdate) {
            if (fragmentView.get() != null) {
                reminder.setContactGroup(contactGroup);
                reminder.setInterval(interval);
                reminder.set(isUpdate, new Reminder.ActionCallback() {
                    @Override
                    public void onSuccess() {
                        fragmentView.get().onSetReminderSuccess();
                    }

                    @Override
                    public void onError(int errorCode) {
                        fragmentView.get().onSetReminderError("Oops! Please try setting reminder again");
                    }
                });
            }
        }

    }

}
