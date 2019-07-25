package com.example.roomtest.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "book_table")
public class BookEntity {
    @PrimaryKey
    @NonNull
    private String bookId;
    private String name;

    private String publish;

    public String getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public String getPublish() {
        return publish;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    @Override
    public String toString() {
        return "BookEntity{bookId: "+bookId
                +",name:"+name+",publish:"+publish+"}";
    }
}
