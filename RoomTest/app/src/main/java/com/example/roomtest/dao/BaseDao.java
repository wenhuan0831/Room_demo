package com.example.roomtest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.List;

//created by wenhuan
@Dao
public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(T entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertList(List<T> entities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void update(T entity);

    @Delete
    public void delete(T entity);

    @Delete

    public void deleteAll(List<T> entities);

}
