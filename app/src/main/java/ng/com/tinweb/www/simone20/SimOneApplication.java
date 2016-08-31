package ng.com.tinweb.www.simone20;

import android.app.Application;
import android.content.Context;

/**
 * Created by kamiye on 30/08/2016.
 */
public class SimOneApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
