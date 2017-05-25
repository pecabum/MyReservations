package com.restaurants.fragment;

import android.app.Activity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurants.adapter.ReservationsAdapter;
import com.restaurants.dao.Reservation;
import com.restaurants.databinding.FragmentReservationsBinding;
import com.restaurants.interfaces.ReservationsInteractionListener;
import com.restaurants.interfaces.ToolbarActionListener;
import com.restaurants.viewmodel.ReservationsViewModel;

import java.util.List;


public class ReservationsFragment extends LifecycleFragment implements ToolbarActionListener {

    private FragmentReservationsBinding binding;
    private ReservationsAdapter adapter;

    private ReservationsInteractionListener interactionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReservationsInteractionListener) {
            interactionListener = (ReservationsInteractionListener) context;
        }
    }

    /**
     * In case we have an old device
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ReservationsInteractionListener) {
            interactionListener = (ReservationsInteractionListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReservationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new ReservationsAdapter(interactionListener);
        binding.rvReservations.setAdapter(adapter);
        binding.toolbarHolder.setListener(this);
        binding.setListener(interactionListener);

        ReservationsViewModel viewModel = ViewModelProviders.of(this).get(ReservationsViewModel.class);
        viewModel.subscribeToChanges();
        viewModel.getReservations().observe(this, new Observer<List<Reservation>>() {
            @Override
            public void onChanged(@Nullable List<Reservation> reservations) {
                adapter.setReservationsList(reservations);
            }
        });
    }

    @Override
    public void onActionClicked() {
        interactionListener.onSettingsClicked();
    }
}