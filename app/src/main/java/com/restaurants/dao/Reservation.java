package com.restaurants.dao;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.restaurants.model.DateConverter;

import java.util.Date;

@Entity(tableName = "reservations")
@TypeConverters(DateConverter.class)
public class Reservation {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    private Date date;

    @ColumnInfo(name="guests_amount")
    private int guestsAmount;

    private String email;

    @ColumnInfo(name="phone_number")
    private String phoneNumber;

    public Reservation(Date date, int guestsAmount, String email, String phoneNumber) {
        this.date = date;
        this.guestsAmount = guestsAmount;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getGuestsAmount() {
        return guestsAmount;
    }

    public void setGuestsAmount(int guestsAmount) {
        this.guestsAmount = guestsAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

