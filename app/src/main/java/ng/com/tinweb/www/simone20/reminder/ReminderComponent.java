package ng.com.tinweb.www.simone20.reminder;

import dagger.Subcomponent;

/**
 * Created by kamiye on 13/04/2017.
 */

@ReminderScope
@Subcomponent(modules = ReminderModule.class)
public interface ReminderComponent {

    void inject(ReminderFragment fragment);

}
