package com.example.roomtest;

import com.example.roomtest.entity.BookEntity;
import com.example.roomtest.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class DataGenertor {

    private static final String[] NAME = new String[]{
            "刘德华", "黎明", "郭富城", "张学友", "大忽悠"};
    private static final String[] ADDRESS = new String[]{
            "香港", "香港", "香港", "香港", "辽宁"};

    private static final String[] BOOK_NAME = new String[]{
            "C++", "面向对象程序与设计", "三字经", "九阴真经", "天之道"};
    private static final String[] BOOk_PUBLISH = new String[]{
            "新华", "新华", "新华", "新华", "出版社"};
    public static List<UserEntity> generateUsers() {
        List<UserEntity> userEntities = new ArrayList<>();
        for (int i = 0; i < NAME.length; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(NAME[i]);
            userEntity.setAddress(ADDRESS[i]);
            userEntities.add(userEntity);
        }
        return userEntities;
    }

    public static List<BookEntity> generateBooks() {
        List<BookEntity> bookEntities = new ArrayList<>();
        for (int i = 0; i < BOOK_NAME.length; i++) {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setName(BOOK_NAME[i]);
            bookEntity.setBookId(String.valueOf(BOOk_PUBLISH[i].hashCode()));
            bookEntity.setPublish(BOOk_PUBLISH[i]);
            bookEntities.add(bookEntity);

        }
        return bookEntities;
    }


}
