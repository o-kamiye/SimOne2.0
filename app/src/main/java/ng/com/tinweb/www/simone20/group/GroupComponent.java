package ng.com.tinweb.www.simone20.group;

import dagger.Subcomponent;

/**
 * Created by kamiye on 13/04/2017.
 */

@GroupScope
@Subcomponent(modules = GroupModule.class)
public interface GroupComponent {

    void inject(GroupFragment fragment);

}
