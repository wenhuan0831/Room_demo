package com.example.roomtest.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.roomtest.dao.BookDao;
import com.example.roomtest.dao.UserDao;
import com.example.roomtest.entity.BookEntity;
import com.example.roomtest.entity.UserEntity;

@Database(entities = {UserEntity.class, BookEntity.class}, version = 3, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static final String DB_NAME = "data_base.db";
    private static volatile AppDataBase instance;

    public static AppDataBase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDataBase.class) {
                if (instance == null) {
                    instance = create(context);
                }
            }

        }
        return instance;
    }

    private static AppDataBase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDataBase.class,
                DB_NAME).
                addMigrations(MIGRATION_1_2).allowMainThreadQueries().build();
    }

    public abstract UserDao getUserDao();

    public abstract BookDao getBookDao();


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE 'book_table' (`bookId` TEXT, "
                    + "`name` TEXT,'publish' TEXT, PRIMARY KEY(`bookId`))");
        }
    };
}
