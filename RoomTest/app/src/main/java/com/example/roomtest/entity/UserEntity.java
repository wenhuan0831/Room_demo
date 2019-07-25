package com.example.roomtest.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class UserEntity {
    @PrimaryKey (autoGenerate = true)
    private int userId;

    private String userName;
    private String address;

    public int getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getUserName() {
        return userName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserEntity{"+"useId:" + userId
                + ",address:" + address
                + ",userName:" + userName+"}";
    }
}
