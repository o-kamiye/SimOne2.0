package ng.com.tinweb.www.simone20.reminder;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.data.group.SimOneGroup;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 08/09/2016.
 */
class ReminderPresenter implements IReminderPresenter {

    private WeakReference<IReminderView> reminderView;
    private SimOneReminder simOneReminder;

    @Inject
    ReminderPresenter(SimOneReminder simOneReminder, IReminderView reminderView) {
        this.simOneReminder = simOneReminder;
        this.reminderView = new WeakReference<>(reminderView);
    }

    @Override
    public void loadReminders() {
        simOneReminder.getAll(false, new SimOneReminder.GetAllCallback() {
            @Override
            public void onSuccess(HashMap<String, String> metaData,
                                  List<SimOneReminder> simOneReminders) {
                if (reminderView.get() != null) {
                    reminderView.get().onRemindersLoaded(simOneReminders);
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
            // TODO get the simOneReminder from the contactId
            // TODO call the remove method on the simOneReminder object
            // TODO if the removal was successful, then call the view's remove successful callback method
            reminderView.get().showDeleteSuccessInfo();
            // TODO if the removal was unsuccessful, then call the view's remove unsuccessful callback method
            reminderView.get().showDeleteErrorInfo();
        }
    }

    static class SetReminderPresenter implements IReminderFragmentPresenter {

        private WeakReference<IReminderView.IReminderFragmentView> fragmentView;
        private SimOneReminder simOneReminder;
        private SimOneGroup simOneGroup;

        SetReminderPresenter(IReminderView.IReminderFragmentView fragmentView,
                             SimOneReminder simOneReminder, SimOneGroup simOneGroup) {
            this.fragmentView = new WeakReference<>(fragmentView);
            this.simOneReminder = simOneReminder;
            this.simOneGroup = simOneGroup;
        }

        @Override
        public void setReminder(String contactGroup, int interval, boolean isUpdate) {
            if (fragmentView.get() != null) {
                simOneReminder.setContactGroup(contactGroup);
                simOneReminder.setInterval(interval);
                simOneReminder.set(isUpdate, new SimOneReminder.ActionCallback() {
                    @Override
                    public void onSuccess() {
                        fragmentView.get().onSetReminderSuccess();
                    }

                    @Override
                    public void onError(int errorCode) {
                        fragmentView.get().onSetReminderError("Oops! Please try setting Reminder again");
                    }
                });
            }
        }

        @Override
        public void loadGroupNames() {
            simOneGroup.getAll(new SimOneGroup.GetAllCallback() {
                @Override
                public void onSuccess(List<SimOneGroup> groups) {
                    Map<String, Integer> groupsMap = new HashMap<>();
                    for (SimOneGroup group : groups) {
                        groupsMap.put(group.getName(), group.getInterval());
                    }
                    if (fragmentView.get() != null) {
                        fragmentView.get().onGroupNamesLoaded(groupsMap);
                    }
                }

                @Override
                public void onError(int errorCode) {
                    String message = "An error occurred while fetching group names";
                    fragmentView.get().onGroupNamesLoadingError(message);
                }
            });
        }

    }

}
