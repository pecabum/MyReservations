package com.restaurants.util;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.restaurants.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingAdapters {

    @BindingAdapter({"bind:date"})
    public static void setDate(TextView textView, Date date) {
        if (date != null) {
            textView.setText(StringUtils.formatDate(textView.getContext(), date));
        }
    }

    @BindingAdapter({"bind:date"})
    public static void setDate(TextView textView, ObservableField<Date> date) {
        if (date != null && date.get() != null) {
            textView.setText(StringUtils.formatDate(textView.getContext(), date.get()));
        }
    }

    @BindingAdapter({"app:visibility"})
    public static void setVisibility(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


}
