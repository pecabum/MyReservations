package com.restaurants.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.restaurants.ReservationsApplication;
import com.restaurants.dao.ReservationDao;
import com.restaurants.model.AppDatabase;
import com.restaurants.util.AdminService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    final ReservationsApplication app;

    public AppModule(ReservationsApplication app) {
        this.app = app;
    }

    @Provides
    Context provideApplicationContext() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    ReservationDao provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME).build().reservationDao();
    }

    @Provides
    @Singleton
    AdminService provideAdminService(Context context) {
        return new AdminService(context);
    }

}