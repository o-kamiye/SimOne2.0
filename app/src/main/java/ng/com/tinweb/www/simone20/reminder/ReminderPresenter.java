package ng.com.tinweb.www.simone20.reminder;

import java.lang.ref.WeakReference;
import java.security.acl.Group;

import ng.com.tinweb.www.simone20.data.reminder.Reminder;

/**
 * Created by kamiye on 08/09/2016.
 */
class ReminderPresenter implements IReminderPresenter {

    private WeakReference<IReminderView> reminderView;

    ReminderPresenter(IReminderView reminderView) {
        this.reminderView = new WeakReference<>(reminderView);
    }

    @Override
    public void setWeeklyReminderCount() {
        // TODO calling view's setWeeklyReminder() method should be done asynchronously
        int weeklyRemindersCount = 2;
        if (reminderView.get() != null)
            reminderView.get().setWeekReminderTextView(weeklyRemindersCount);
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

    static class AddReminderPresenter implements IReminderFragmentPresenter {

        private WeakReference<IReminderView.IReminderFragmentView> fragmentView;
        private Reminder reminder;

        AddReminderPresenter(IReminderView.IReminderFragmentView fragmentView,
                             Reminder reminder) {
            this.fragmentView = new WeakReference<>(fragmentView);
            this.reminder = reminder;
        }

        @Override
        public void addReminder(String contactGroup, int interval) {
            if (fragmentView.get() != null) {
                reminder.setContactGroup(contactGroup);
                reminder.setInterval(interval);
                reminder.create(new Reminder.ActionCallback() {
                    @Override
                    public void onSuccess() {
                        fragmentView.get().onAddReminderSuccess();
                    }

                    @Override
                    public void onError(int errorCode) {
                        fragmentView.get().onAddReminderError("Oops! Please try setting reminder again");
                    }
                });
            }
        }

    }

}
