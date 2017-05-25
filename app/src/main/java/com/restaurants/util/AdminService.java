package com.restaurants.util;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

@Singleton
public class AdminService {

    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_ADMIN_PREFS = "admin_prefs";

    private SharedPreferences preferences;

    public AdminService(Context context) {
        preferences = context.getSharedPreferences(KEY_ADMIN_PREFS, Context.MODE_PRIVATE);
    }

    public void setAdminEmail(String userEmail) {
        preferences.edit().putString(KEY_USER_EMAIL, userEmail).apply();
    }

    public String getAdminEmail() {
        return preferences.getString(KEY_USER_EMAIL,"");
    }

}
