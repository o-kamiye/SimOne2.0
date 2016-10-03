package ng.com.tinweb.www.simone20.data.group;

import android.content.Context;

import ng.com.tinweb.www.simone20.SimOne;

/**
 * Created by kamiye on 04/10/2016.
 */

public class SimOneGroup {

    private DataStore dataStore;

    /**
     * TODO this class to be further implemented in the group story
     */
    public SimOneGroup() {
        initialiseDateStore();
    }

    public int getInterval() {
        return 0;
    }

    private void initialiseDateStore() {
        Context context = SimOne.getContext();
        this.dataStore = new GroupDb(context);
    }
}
