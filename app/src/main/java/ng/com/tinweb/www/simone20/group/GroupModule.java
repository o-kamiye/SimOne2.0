package ng.com.tinweb.www.simone20.group;

import dagger.Module;
import dagger.Provides;
import ng.com.tinweb.www.simone20.data.group.SimOneGroup;

/**
 * Created by kamiye on 13/04/2017.
 */

@Module
public class GroupModule {

    private IGroupView view;

    GroupModule(IGroupView view) {
        this.view = view;
    }

    @Provides
    @GroupScope
    GroupPresenter providesPresenter(SimOneGroup group) {
        return new GroupPresenter(group, view);
    }
}
