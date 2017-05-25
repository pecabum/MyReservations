package com.restaurants.fragment;

import android.app.Activity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.restaurants.R;
import com.restaurants.ReservationsApplication;
import com.restaurants.adapter.ReservationsAdapter;
import com.restaurants.dao.Reservation;
import com.restaurants.databinding.FragmentReservationsBinding;
import com.restaurants.databinding.FragmentSettingsBinding;
import com.restaurants.interfaces.ReservationsInteractionListener;
import com.restaurants.interfaces.ToolbarActionListener;
import com.restaurants.util.AdminService;
import com.restaurants.util.StringUtils;
import com.restaurants.viewmodel.ReservationsViewModel;

import java.util.List;

import javax.inject.Inject;


public class SettingsFragment extends LifecycleFragment implements ToolbarActionListener{

    private FragmentSettingsBinding binding;
    private ReservationsInteractionListener interactionListener;

    @Inject
    protected AdminService adminService;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReservationsApplication.getComponent(getActivity()).inject(this);
        setupUI();
    }

    private void setupUI() {
        binding.etEmail.setText(adminService.getAdminEmail());
        binding.toolbarHolder.setListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void onActionClicked() {
        Editable text = binding.etEmail.getText();
        if (StringUtils.isValidEmail(text)) {
            adminService.setAdminEmail(text.toString());
            interactionListener.back();
        } else {
            Toast.makeText(getActivity(), R.string.error_invald_email, Toast.LENGTH_SHORT).show();
        }
    }
}