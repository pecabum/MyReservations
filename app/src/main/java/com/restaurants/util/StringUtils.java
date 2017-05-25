package com.restaurants.util;

import android.content.Context;
import android.text.TextUtils;

import com.restaurants.R;
import com.restaurants.dao.Reservation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static String formatDate(Context context, Date date) {
        SimpleDateFormat sdf2 = new SimpleDateFormat(context.getString(R.string.date_format));
        return sdf2.format(date);
    }


    public static String formatEmail(Context context,Reservation reservation) {
        return context.getString(R.string.email_content,
                reservation.getGuestsAmount(),
                formatDate(context, reservation.getDate()),
                reservation.getPhoneNumber());

    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
