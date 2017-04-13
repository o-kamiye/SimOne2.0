package ng.com.tinweb.www.simone20.today;

import dagger.Module;
import dagger.Provides;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;

/**
 * Created by kamiye on 13/04/2017.
 */

@Module
public class TodayModule {

    private ITodayView view;

    TodayModule(ITodayView view) {
        this.view = view;
    }

    @Provides
    @TodayScope
    ITodayView providesView() {
        return view;
    }

    @Provides
    @TodayScope
    TodayPresenter providesPresenter(SimOneReminder reminder, ITodayView view) {
        return new TodayPresenter(reminder, view);
    }

}
