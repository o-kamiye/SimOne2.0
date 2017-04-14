package ng.com.tinweb.www.simone20.reminder;

import dagger.Module;
import dagger.Provides;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 13/04/2017.
 */

@Module
public class ReminderModule {

    private IReminderView view;

    ReminderModule(IReminderView view) {
        this.view = view;
    }

    @Provides
    @ReminderScope
    ReminderPresenter providesPresenter(SimOneReminder reminder) {
        return new ReminderPresenter(reminder, view);
    }

}
