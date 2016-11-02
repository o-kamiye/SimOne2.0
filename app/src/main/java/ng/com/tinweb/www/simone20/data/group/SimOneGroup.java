package ng.com.tinweb.www.simone20.data.group;

import android.content.Context;

import ng.com.tinweb.www.simone20.SimOne;

/**
 * Created by kamiye on 04/10/2016.
 */

public class SimOneGroup {

    public static final int DB_INSERT_ERROR = 99;
    public static final int GROUP_EXISTS_ERROR = 98;

    private DataStore dataStore;

    private String name;
    private int interval;

    /**
     * TODO this class to be further implemented in the group story
     */
    public SimOneGroup(String name, int interval) {
        initialiseDateStore();
        this.name = name;
        this.interval = interval;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public int getInterval() {
        return interval;
    }

    public void create(ActionCallback callback) {
        dataStore.save(name, interval, callback);
    }

    private void initialiseDateStore() {
        Context context = SimOne.getContext();
        this.dataStore = new GroupDb(context);
    }

    public interface ActionCallback {
        void onSuccess();
        void onError(int errorCode);
    }
}
