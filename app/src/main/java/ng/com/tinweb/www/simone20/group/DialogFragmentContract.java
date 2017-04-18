package ng.com.tinweb.www.simone20.group;

/**
 * Created by kamiye on 15/04/2017.
 */

public class DialogFragmentContract {

    interface View {

        void onAddGroupSuccess();

        void onAddGroupError(String message);
    }

    interface Presenter {

        void addGroup(String name, int interval, boolean isEdit);
    }

}
