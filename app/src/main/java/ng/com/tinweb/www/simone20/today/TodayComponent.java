package ng.com.tinweb.www.simone20.today;

import dagger.Subcomponent;

/**
 * Created by kamiye on 13/04/2017.
 */

@TodayScope
@Subcomponent(modules = TodayModule.class)
public interface TodayComponent {

    void inject(TodayFragment todayFragment);

}
