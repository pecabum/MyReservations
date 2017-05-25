package com.restaurants;

import android.app.Application;
import android.content.Context;

import com.restaurants.di.AppComponent;
import com.restaurants.di.AppModule;
import com.restaurants.di.DaggerAppComponent;

/**
 * The packages are constructed that way because of the small project.
 * I'm grouping the classes and sub-packages by context when the project is bigger
 */
public class ReservationsApplication extends Application {

    private AppComponent appComponent;

    public static AppComponent getComponent(Context context) {
        return ((ReservationsApplication) context.getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

}
