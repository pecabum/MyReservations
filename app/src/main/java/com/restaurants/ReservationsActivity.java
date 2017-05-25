package com.restaurants;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LifecycleActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.restaurants.dao.Reservation;
import com.restaurants.fragment.CreateReservationFragment;
import com.restaurants.fragment.ReservationsFragment;
import com.restaurants.fragment.SettingsFragment;
import com.restaurants.interfaces.ReservationsInteractionListener;
import com.restaurants.util.AdminService;
import com.restaurants.util.StringUtils;

import javax.inject.Inject;

public class ReservationsActivity extends LifecycleActivity implements ReservationsInteractionListener {

    @Inject
    protected AdminService adminService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        ReservationsApplication.getComponent(getApplication()).inject(this);

        // preventing recreation when orientation changes
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ReservationsFragment())
                    .commit();
            // There are few permission libraries which are suitable but no need them for a single one
            checkPermissionAndPrint();
        }
    }

    private void checkPermissionAndPrint() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
        if (result == PackageManager.PERMISSION_GRANTED) {
            printLastCalledNumber();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
    }

    private void printLastCalledNumber() {
        String lastDialed = CallLog.Calls.getLastOutgoingCall(getApplicationContext());
        Toast.makeText(this, getString(R.string.message_last_called_number) + lastDialed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    printLastCalledNumber();
                } else {
                    Toast.makeText(this, R.string.error_permission_calls_denied, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onCreateReservationClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CreateReservationFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSendEmailClicked(Reservation reservation) {
        openEmailClient(reservation);
    }

    private void openEmailClient(Reservation reservation) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{reservation.getEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.reservations_for, StringUtils.formatDate(this, reservation.getDate())));

        // Send hidden copy to the admin
        intent.putExtra(Intent.EXTRA_BCC, new String[]{adminService.getAdminEmail()});
        intent.putExtra(Intent.EXTRA_TEXT, StringUtils.formatEmail(this, reservation));
        startActivity(Intent.createChooser(intent, getString(R.string.send_email)));
    }


    @Override
    public void onSettingsClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void back() {

        // I prefer my own architecture but no time for that
        getSupportFragmentManager().popBackStack();
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
