package com.restaurants.interfaces;

import com.restaurants.dao.Reservation;

public interface ReservationsInteractionListener {
    void onCreateReservationClicked();
    void onSendEmailClicked(Reservation reservation);
    void onSettingsClicked();
    void back();
}
