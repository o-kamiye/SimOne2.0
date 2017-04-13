package ng.com.tinweb.www.simone20.di;

import javax.inject.Singleton;

import dagger.Component;
import ng.com.tinweb.www.simone20.contact.ContactListDialogFragment;
import ng.com.tinweb.www.simone20.group.AddGroupDialogFragment;
import ng.com.tinweb.www.simone20.group.GroupFragment;
import ng.com.tinweb.www.simone20.reminder.ReminderComponent;
import ng.com.tinweb.www.simone20.reminder.ReminderModule;
import ng.com.tinweb.www.simone20.reminder.SetReminderDialogFragment;
import ng.com.tinweb.www.simone20.today.TodayComponent;
import ng.com.tinweb.www.simone20.today.TodayModule;

/**
 * Created by kamiye on 13/04/2017.
 */

@Singleton
@Component (modules = AppModule.class)
public interface AppComponent {

    TodayComponent subComponent(TodayModule module);

    ReminderComponent subComponent(ReminderModule module);

    void inject(GroupFragment groupFragment);
    void inject(SetReminderDialogFragment dialogFragment);
    void inject(ContactListDialogFragment dialogFragment);
    void inject(AddGroupDialogFragment dialogFragment);
}