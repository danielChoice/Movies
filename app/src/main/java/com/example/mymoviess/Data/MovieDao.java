package com.example.mymoviess.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.mymoviess.FavouriteMovie;
import com.example.mymoviess.Movie;

import java.util.List;

@androidx.room.Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    //поулчает фильм по id, который присвоен db
    @Query("SELECT * FROM movies WHERE elementId == :id")
    Movie getMovieByElementId(int id);

    //получает фильм по id, присвоеному сервисом
    @Query("SELECT * FROM movies WHERE movieId == :id")
    Movie getMovieByMovieId(int id);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM favourite_movies")
    LiveData<List<FavouriteMovie>> getAllFavouriteMovies();

    @Insert
    void insertFavouriteMovie(FavouriteMovie favouriteMovie);

    @Delete
    void deleteFavouriteMovie(FavouriteMovie favouriteMovie);

    //получает фильм по id, присвоеному сервисом
    @Query("SELECT * FROM favourite_movies WHERE movieId == :id")
    FavouriteMovie getFavouriteMovieByMovieId(int id);







}
