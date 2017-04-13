package ng.com.tinweb.www.simone20.data.group;

import android.content.Context;

import java.util.List;

/**
 * Created by kamiye on 04/10/2016.
 */

public class SimOneGroup {

    public static final int DB_INSERT_ERROR = 99;
    public static final int GROUP_EXISTS_ERROR = 98;
    public static final int UNKNOWN_ERROR = 100;

    private DataStore dataStore;

    private String name;
    private int interval;
    private int members;

    /**
     * TODO this class to be further implemented in the group story
     */

    public SimOneGroup(Context context) {
        initialiseDateStore(context);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(int members) {
        this.members = members;
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

    public void getAll(GetAllCallback callback) {
        dataStore.getMultiple(callback);
    }

    private void initialiseDateStore(Context context) {
        this.dataStore = new GroupDbHelper(context);
    }

    public interface ActionCallback {
        void onSuccess();
        void onError(int errorCode);
    }

    public interface GetAllCallback {
        void onSuccess(List<SimOneGroup> groups);
        void onError(int errorCode);
    }
}
