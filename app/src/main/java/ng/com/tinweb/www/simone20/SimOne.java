package ng.com.tinweb.www.simone20;

import android.app.Application;
import android.content.Context;

import ng.com.tinweb.www.simone20.di.AppComponent;
import ng.com.tinweb.www.simone20.di.AppModule;
import ng.com.tinweb.www.simone20.di.DaggerAppComponent;

/**
 * Created by kamiye on 30/08/2016.
 */
public class SimOne extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        SimOneContact contact = new SimOneContact(this);
//
//        new PhoneContact().generateMany(this);
//
//        contact.syncAll(new SimOneContact.SyncCallback() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(SimOne.this, "Contact successfully loaded", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                Toast.makeText(SimOne.this, errorMessage, Toast.LENGTH_SHORT).show();
//            }
//        });

        // Dagger
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static SimOne get(Context context) {
        return (SimOne) context;
    }

}
