package ng.com.tinweb.www.simone20.group;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * GroupRemindersPresenter - Presenter
 */
public class GroupRemindersPresenter implements GroupRemindersContract.Presenter {

    private WeakReference<GroupRemindersContract.View> view;
    private SimOneReminder reminder;

    GroupRemindersPresenter(GroupRemindersContract.View view, SimOneReminder reminder) {
        this.view = new WeakReference<>(view);
        this.reminder = reminder;
    }

    @Override
    public void loadGroupReminders(String groupName) {
        reminder.getGroupReminders(groupName, new SimOneReminder.GetAllCallback() {
            @Override
            public void onSuccess(HashMap<String, String> metaData, List<SimOneReminder> reminders) {
                if (view.get() != null) view.get().onRemindersLoaded(reminders);
            }

            @Override
            public void onError(int errorCode) {
                if (view.get() != null) view.get().onRemindersLoadingError(errorCode);
            }
        });
    }
}
