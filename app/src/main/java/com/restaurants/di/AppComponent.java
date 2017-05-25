package com.restaurants.di;

import com.restaurants.ReservationsActivity;
import com.restaurants.fragment.SettingsFragment;
import com.restaurants.viewmodel.CreateReservationViewModel;
import com.restaurants.viewmodel.ReservationsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(ReservationsViewModel reservationsViewModel);
    void inject(ReservationsActivity reservationsActivity);
    void inject(CreateReservationViewModel createReservationViewModel);
    void inject(SettingsFragment settingsFragment);
}