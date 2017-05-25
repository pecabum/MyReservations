package com.restaurants.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.restaurants.dao.Reservation;
import com.restaurants.dao.ReservationDao;

@Database(entities = {Reservation.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "reservations-db";

    public abstract ReservationDao reservationDao();
}
