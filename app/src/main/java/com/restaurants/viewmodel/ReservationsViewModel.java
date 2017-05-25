package com.restaurants.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.restaurants.ReservationsApplication;
import com.restaurants.dao.ReservationDao;
import com.restaurants.model.AppDatabase;
import com.restaurants.dao.Reservation;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

public class ReservationsViewModel extends AndroidViewModel {

    @Inject
    protected ReservationDao reservationDao;

    private LiveData<List<Reservation>> reservations;

    public ReservationsViewModel(Application application) {
        super(application);
        ReservationsApplication.getComponent(application).inject(this);
    }

    public void subscribeToChanges() {
        reservations = reservationDao.getAll();
    }

    public LiveData<List<Reservation>> getReservations() {
        return reservations;
    }

}
