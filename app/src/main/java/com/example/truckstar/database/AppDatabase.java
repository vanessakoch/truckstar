package com.example.truckstar.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.truckstar.entities.Helper;
import com.example.truckstar.entities.Provider;
import com.example.truckstar.entities.Trip;
import com.example.truckstar.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Provider.class, User.class, Trip.class, Helper.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProviderDao providerDao();
    public abstract UserDao userDao();
    public abstract TripDao tripDao();
    public abstract HelperDao helperDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database").fallbackToDestructiveMigration().build();

                }
            }
        }
        return INSTANCE;
    }
}