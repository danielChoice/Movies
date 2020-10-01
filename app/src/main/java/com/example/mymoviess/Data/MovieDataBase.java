package com.example.mymoviess.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mymoviess.FavouriteMovie;
import com.example.mymoviess.Movie;

@Database(entities = {Movie.class, FavouriteMovie.class}, version = 4, exportSchema = false)
public abstract class MovieDataBase extends RoomDatabase {
    private static MovieDataBase dataBase;
    private static final String TABLE_NAME = "movies.db";
    private static Object LOCK = new Object();

    public static MovieDataBase getInstance(Context context){
        synchronized (LOCK) {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, MovieDataBase.class, TABLE_NAME).fallbackToDestructiveMigration().build();
            }
            return dataBase;
        }

    }

    public abstract MovieDao movieDao();
}
