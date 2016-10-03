package ng.com.tinweb.www.simone20.reminder;

/**
 * Created by kamiye on 08/09/2016.
 */
interface IReminderView {

    void setWeekReminderTextView(int total);

    void showEditReminderPopUp();

    void showDeleteSuccessInfo();

    void showDeleteErrorInfo();

    interface IReminderFragmentView {

        void onAddReminderSuccess();

        void onAddReminderError(String message);

    }
}
