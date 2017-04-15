package ng.com.tinweb.www.simone20.reminder;

import java.util.Map;

/**
 * Created by kamiye on 14/04/2017.
 */

class DialogFragmentContract {


    interface View {

        void onSetReminderSuccess();

        void onSetReminderError(String message);

        void onGroupNamesLoaded(Map<String, Integer> groupsMap);

        void onGroupNamesLoadingError(String message);

    }

    interface Presenter {

        void setReminder(String contactGroup, int interval, boolean isUpdate);

        void loadGroupNames();

    }

}
