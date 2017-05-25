package com.restaurants.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.databinding.ObservableField;
import android.os.AsyncTask;

import com.restaurants.ReservationsApplication;
import com.restaurants.dao.Reservation;
import com.restaurants.dao.ReservationDao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class CreateReservationViewModel extends AndroidViewModel {

    public static final int MIN_PEOPLE = 1;
    public static final int MAX_PEOPLE = 15;
    @Inject
    protected ReservationDao reservationDao;

    private ObservableField<Date> date = new ObservableField<>();

    public CreateReservationViewModel(Application application) {
        super(application);
        ReservationsApplication.getComponent(application).inject(this);
    }

    public void insertReservation(final Reservation reservation) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // no check for existing reservation, that might be changed
                reservationDao.insertReservation(reservation);

            }
        });
    }

    public ObservableField<Date> getDate() {
        return date;
    }
}
