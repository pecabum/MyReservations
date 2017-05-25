package com.restaurants.fragment;

import android.app.Activity;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fastaccess.datetimepicker.DatePickerFragmentDialog;
import com.fastaccess.datetimepicker.DateTimeBuilder;
import com.fastaccess.datetimepicker.callback.DatePickerCallback;
import com.fastaccess.datetimepicker.callback.TimePickerCallback;
import com.restaurants.R;
import com.restaurants.adapter.ReservationsAdapter;
import com.restaurants.dao.Reservation;
import com.restaurants.databinding.FragmentCreateReservationBinding;
import com.restaurants.databinding.FragmentReservationsBinding;
import com.restaurants.interfaces.ReservationsInteractionListener;
import com.restaurants.interfaces.ToolbarActionListener;
import com.restaurants.util.StringUtils;
import com.restaurants.viewmodel.CreateReservationViewModel;
import com.restaurants.viewmodel.ReservationsViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CreateReservationFragment extends LifecycleFragment implements DatePickerCallback, TimePickerCallback, ToolbarActionListener {

    private static final int REQUEST_CODE_DATE = 1022;
    private static final String TAG_DIALOG = "date-picker";

    private FragmentCreateReservationBinding binding;
    private CreateReservationViewModel viewModel;
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
        binding = FragmentCreateReservationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CreateReservationViewModel.class);

        setupUI();
    }

    private void setupUI() {

        // This listener can be in the binding but will keep it that way
        binding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog();
            }
        });
        binding.toolbarHolder.setListener(this);

        // For example reservation can be for max 15 people
        binding.numberPicker.setMinValue(CreateReservationViewModel.MIN_PEOPLE);
        binding.numberPicker.setMaxValue(CreateReservationViewModel.MAX_PEOPLE);

        // Custom number picker
        binding.numberPicker.getButtonPlusView().setTextSize(22);
        binding.numberPicker.getButtonMinusView().setTextSize(22);
        binding.numberPicker.getTextValueView().setTextSize(22);

        binding.setDate(viewModel.getDate());
    }

    private void showDateTimeDialog() {
        long time = new Date().getTime();
        // Because we have no design for it i am using this library - it has compatibility with the old APIs
        DatePickerFragmentDialog dialog = DatePickerFragmentDialog.newInstance(
                DateTimeBuilder.newInstance()
                        .withTime(true)
                        .with24Hours(true)
                        .withMinDate(time)
                        .withSelectedDate(time)
                        .withCurrentHour(12)
                        .withCurrentMinute(30));

        dialog.setTargetFragment(this, REQUEST_CODE_DATE);

        // Ahh... The library fragment Tag doesn't exist... well is it not so good library
        dialog.show(getChildFragmentManager(), TAG_DIALOG);
    }

    @Override
    public void onDateSet(long date) {/* not using */}

    @Override
    public void onTimeSet(long timeOnly, long dateWithTime) {
        viewModel.getDate().set(new Date(dateWithTime));
    }

    @Override
    public void onActionClicked() {

        // Simple check - ugly but no time for better
        if (viewModel.getDate().get() != null && !TextUtils.isEmpty(binding.etEmail.getText())
                && !TextUtils.isEmpty(binding.etPhone.getText())) {

            if (StringUtils.isValidEmail(binding.etEmail.getText())) {
                viewModel.insertReservation(new Reservation(viewModel.getDate().get(),
                        binding.numberPicker.getValue(),
                        binding.etEmail.getText().toString(),
                        binding.etPhone.getText().toString()));
                interactionListener.back();
            } else {
                Toast.makeText(getActivity(), R.string.error_invald_email, Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), R.string.error_fill_all_fields, Toast.LENGTH_SHORT).show();
        }
    }
}