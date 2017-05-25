package com.restaurants.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.restaurants.model.DateConverter;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface ReservationDao {

    @Query("SELECT * FROM reservations")
    LiveData<List<Reservation>> getAll();

    @Query("SELECT * FROM reservations WHERE reservations.email = :mail AND reservations.date = :reservationDate")
    List<Reservation> findReservations(String mail, Date reservationDate);

    @Insert(onConflict = REPLACE)
    void insertReservation(Reservation reservation);
    // TODO : We can also create request for deleting all reservations with date older than the current
}
