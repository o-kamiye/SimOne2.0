package ng.com.tinweb.www.simone20.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.data.group.SimOneGroup;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 13/04/2017.
 */

@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application providesApplication() {
        return application;
    }

    @Provides
    SimOneReminder providesSimOneReminder(Application application) {
        return new SimOneReminder(application);
    }

    @Provides
    SimOneContact providesSimOneContact(Application application) {
        return new SimOneContact(application);
    }

    @Provides
    SimOneGroup providesSimOneGroup(Application application) {
        return new SimOneGroup(application);
    }
}
