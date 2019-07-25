package com.example.roomtest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;

import com.example.roomtest.entity.UserEntity;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<UserEntity> {


    @Query("SELECT * From UserEntity Where userId =:userId")
    public UserEntity query(int userId);

    @Query("SELECT * From UserEntity")
    public List<UserEntity> queryAll();

    //根据字段查询
    @Query("SELECT * FROM UserEntity WHERE userName= :name")
    UserEntity getUserByName(String name);

}
