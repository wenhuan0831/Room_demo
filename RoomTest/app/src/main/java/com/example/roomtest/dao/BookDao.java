package com.example.roomtest.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;


import com.example.roomtest.entity.BookEntity;
import com.example.roomtest.entity.UserEntity;

import java.util.List;

@Dao
public interface BookDao extends BaseDao<BookEntity> {

    @Query("SELECT * From book_table Where bookId =:bookId")
    public BookEntity query(String bookId);

    @Query("SELECT * From book_table")
    public List<BookEntity> queryAll();

    //根据字段查询
    @Query("SELECT * FROM book_table WHERE name= :name")
    BookEntity getBookByName(String name);

    @Query("DELETE FROM book_table")
    public void deleteAllBook();
}
